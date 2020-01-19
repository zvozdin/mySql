package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

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
        parametersNumberValidation(COMMAND_SAMPLE, command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        DataSet delete = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            delete.put(columnName, value);
        }

        manager.deleteRow(tableName, delete);
        view.write(String.format("Record '%s' deleted.", delete.getValues().get(0)));
        // print table with values
        new Find(manager, view).printTableHeader(tableName);
        new Find(manager, view).printValues(tableName);
    }
}