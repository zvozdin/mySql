package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.LinkedHashSet;
import java.util.Set;

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

        Set<String> columns = new LinkedHashSet<>();
        for (int index = 2; index < data.length; index++) {
            columns.add(data[index]);
        }

        manager.createTable(tableName, columns);
        view.write(String.format("Table '%s' created.", tableName));
    }
}