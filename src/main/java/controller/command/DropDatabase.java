package controller.command;

import model.DatabaseManager;
import view.View;

public class DropDatabase implements Command {

    private static final String COMMAND_SAMPLE = "dropDatabase|databaseName";
    private DatabaseManager manager;
    private View view;

    public DropDatabase(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropDatabase|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String[] commandToConnect = COMMAND_SAMPLE.split("\\|");
        if (data.length != commandToConnect.length) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected %s. You enter ==> %s",
                    commandToConnect.length, data.length));
        }

        String databaseName = data[1];
        manager.dropDatabase(databaseName);
        view.write(String.format("Database '%s' deleted.", databaseName));
    }
}