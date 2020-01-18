package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.util.List;

public class Update implements Command {

    private static final String COMMAND_SAMPLE = "update|tableName|column1|value1|column2|value2";
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
        String[] commandToInsert = COMMAND_SAMPLE.split("\\|");
        if (data.length != commandToInsert.length) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected %s. You enter ==> %s.\n" +
                            "Use command 'update|tableName|column1|value1|column2|value2'",
                    commandToInsert.length, data.length));
        }

        DataSet set = new DataSet();
        set.put(data[2],data[3]);
//        for (int index = 1; index < data.length / 2; index++) {
//            String columnName = data[index * 2];
//            String value = data[index * 2 + 1];
//            set.put(columnName, value);
//        }

        DataSet where = new DataSet();
        where.put(data[4],data[5]);

        String tableName = data[1];
        // TODO check table already exists or catch exception in JDBC in update() method and wrap into RuntimeException
        List<String> tables = manager.getTablesNames();
        for (String table : tables) {
            if (tableName.equals(table)) {
                manager.update(tableName, set, where);
                view.write(String.format("Record '%s' updated.", where.getValues().get(0)));
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