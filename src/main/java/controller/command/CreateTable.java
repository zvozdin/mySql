package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

public class CreateTable implements Command {

    private static final String COMMAND_SAMPLE = "create|tableName|columnName";
    private DatabaseManager manager;
    private View view;

    public CreateTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String[] commandToCreate = COMMAND_SAMPLE.split("\\|");
        if (data.length < commandToCreate.length) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected no less than %s. You enter ==> %s. " +
                            "Use command '1'",
                    commandToCreate, data.length));
        }

        DataSet input = new DataSet();
        for (int index = 2; index < data.length; index++) {
            String columnName = data[index];
            String value = "";
            input.put(columnName, value);
        }
        // TODO check db already exists or catch exception in JDBC createTable() method and wrap into RuntimeException
//        List<String> tables = manager.getTablesNames();
//        for (String table : tables) {
//            if (tableName.equals(table)) {
//                throw new IllegalArgumentException(String.format(
//                        "Table '%s' already exists.", tableName));
//            }
//        }

        String tableName = data[1];
        manager.createTable(tableName, input);
        view.write(String.format("Table '%s' created.", tableName));

        // print table
        new Find(manager, view).printTableHeader(tableName);
    }
}