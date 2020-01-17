package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.util.List;

public class Update implements Command {

    private static final String COMMAND_SAMPLE = "update|tableName|column|value";
    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected even count. You enter ==> %s. " +
                            "Use command 'update|tableName|column1|value1|column2|value2|...|columnN|valueN'",
                    data.length));
        }

        String[] commandToInsert = COMMAND_SAMPLE.split("\\|");
        if (data.length < commandToInsert.length) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected min %s. You enter ==> %s. " +
                            "Use command 'update|tableName|column1|value1|column2|value2|...|columnN|valueN'",
                    commandToInsert.length, data.length));
        }

        DataSet input = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            input.put(columnName, value);
        }

        String tableName = data[1];
        // TODO check table already exists or catch exception in JDBC in insert() method and wrap into RuntimeException
        List<String> tables = manager.getTablesNames();
        for (String table : tables) {
            if (tableName.equals(table)) {
                manager.insert(tableName, input);
                view.write(String.format("Record '%s' updated.", input));
                // print table with values
                new Find(manager, view).printTableHeader(tableName);
                new Find(manager, view).printValues(tableName);
                return;
            }
        }
        throw new IllegalArgumentException(String.format(
                "Table '%s' doesn't exist.", tableName));
    }
}