package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.util.List;

public class Insert implements Command {

    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected even count. You enter ==> %s. " +
                            "Use command 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'",
                    data.length));
        }

        DataSet input = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            input.put(columnName, value);
        }
        String tableName = data[1];
        List<String> tables = manager.getTablesNames();
        for (String table : tables) {
            if (tableName.equals(table)) {
                manager.insert(tableName, input);
                view.write(String.format("Record '%s' added.", input));
                return;
            }
        }
        throw new IllegalArgumentException(String.format(
                "Table '%s' doesn't exist.", tableName));
    }
}