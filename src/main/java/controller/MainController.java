package controller;

import controller.command.*;
import model.DatabaseManager;
import view.View;

public class MainController {

    private View view;
    private DatabaseManager manager;
    private Command[] commands;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{
                new Exit(view),
                new Help(view),
                new Tables(manager, view),
                new Find(manager, view),
                new Unsupported(view)};
    }

    public void run() {
        connectToDB();

        while (true) {
            view.write("Enter a command or help");
            String input = view.read();

            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
        }
    }

    private void connectToDB() {
        view.write("Hello, User!");
        view.write("Enter the Database name, Username and Password in the format: connect|database|user|password");

        while (true) {
            try {
                String input = view.read();
                String[] data = input.split("\\|");
                if (data.length != 4) {
                    throw new IllegalArgumentException("Invalid number of parameters. " +
                            "Expected 4. You enter ==> " + data.length);
                }
                String database = data[1];
                String user = data[2];
                String password = data[3];
                manager.connect(database, user, password);
                break;
            } catch (Exception e) {
                printError(e);
            }
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
