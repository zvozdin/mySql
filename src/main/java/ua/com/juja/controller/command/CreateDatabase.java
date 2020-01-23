package ua.com.juja.controller.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

public class CreateDatabase implements Command {

    private static final String COMMAND_SAMPLE = "newDatabase|databaseName";
    private DatabaseManager manager;
    private View view;

    public CreateDatabase(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("newDatabase|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(COMMAND_SAMPLE, command);

        String[] data = command.split("\\|");
        String databaseName = data[1];

        manager.createDatabase(databaseName);
        view.write(String.format("Database '%s' created.", databaseName));
    }
}