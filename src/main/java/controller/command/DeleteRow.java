package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.util.List;

public class DeleteRow implements Command {

    private static final String COMMAND_SAMPLE = "delete|tableName|column|value";
    private DatabaseManager manager;
    private View view;

    public DeleteRow(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String[] commandToDelete = COMMAND_SAMPLE.split("\\|");
        if (data.length != commandToDelete.length) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected %s. You enter ==> %s. " +
                            "Use command 'delete|tableName|column|value'",
                    commandToDelete.length, data.length));
        }

        DataSet input = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            input.put(columnName, value);
        }

        String tableName = data[1];
        // TODO check table already exists or catch exception in JDBC in deleteRow() method and wrap into RuntimeException
        List<String> tables = manager.getTablesNames();
        for (String table : tables) {
            if (tableName.equals(table)) {
                manager.deleteRow(tableName, input);
                view.write(String.format("Record '%s' deleted.", input));
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