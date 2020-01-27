package ua.com.juja.command;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

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
        parametersNumberValidation(COMMAND_SAMPLE, command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        DataSet input = new DataSet();
        for (int index = 2; index < data.length; index++) {
            String columnName = data[index];
            String value = "";
            input.put(columnName, value);
        }

        manager.createTable(tableName, input);
        view.write(String.format("Table '%s' created.", tableName));
    }
}