package controller.command;

import model.DatabaseManager;
import view.View;

public class Connect implements Command {

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
        try {
            String[] data = command.split("\\|");
            if (data.length != 4) {
                throw new IllegalArgumentException("Invalid number of parameters. " +
                        "Expected 4. You enter ==> " + data.length);
            }
            String database = data[1];
            String user = data[2];
            String password = data[3];
            manager.connect(database, user, password);
        } catch (Exception e) {
            printError(e);
        }
        view.write("Success!");
    }

    private void printError(Exception e) {
        String message = "" + e.getMessage();
        if (e.getCause() != null) {
            message += "\n" + e.getCause().getMessage();
        }
        view.write("Fail for a reason ==> " + message);
        view.write("Try again...");
    }
}