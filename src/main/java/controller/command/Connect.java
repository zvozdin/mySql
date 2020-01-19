package controller.command;

import model.DatabaseManager;
import view.View;

public class Connect implements Command {

    private static final String COMMAND_SAMPLE = "connect|business|root|root";
    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
        commandValidation(COMMAND_SAMPLE, command);
        String[] data = command.split("\\|");
//        String[] commandToConnect = COMMAND_SAMPLE.split("\\|");
//        if (data.length != commandToConnect.length) {
//            throw new IllegalArgumentException(String.format(
//                    "Invalid number of parameters separated by '|'. Expected %s. You enter ==> %s",
//                    commandToConnect.length, data.length));
//        }

        String database = data[1];
        String user = data[2];
        String password = data[3];
        manager.connect(database, user, password);
        view.write("Success!");
    }
}