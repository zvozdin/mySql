package controller;

import controller.command.*;
import model.DatabaseManager;
import view.View;

public class MainController {

    private View view;
    private DatabaseManager manager;
    private Command[] commands;

    public MainController(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(manager, view),
                new IsConnected(manager, view),
                new CreateDatabase(manager, view),
                new DropDatabase(manager, view),
                new Tables(manager, view),
                new CreateTable(manager, view),
                new DropTable(manager, view),
                new Find(manager, view),
                new Insert(manager, view),
                new Update(manager, view),
                new DeleteRow(manager, view),
                new Clear(manager, view),
                new Unsupported(view)
        };
    }

    public void run() {
        view.write("Hello, User!");
        view.write("Enter the Database name, Username and Password in the format: 'connect|database|user|password' " +
                "or help");
        try {
            doWork();
        } catch (ExitException e) {
            //
        }
    }

    private void doWork() {
        while (true) {
            String input = view.read();
            try {
                if (input == null) { // null when interrupt application Ctrl F2
                    new Exit(manager, view).process(input);
                }
                for (Command command : commands) {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                }
            } catch (Exception e) {
                if (e instanceof ExitException) {
                    return;
                }
                printError(e);
            }
            view.write("Enter a command or help");
        }
    }

    private void printError(Exception e) { // TODO wrap SQLException by RuntimeEx(String.format("%s", e)) in JDBC_DBMan
        String message = "" + e.getMessage();
        if (e.getCause() != null) {
//            message += "\n" + e.getCause().getMessage();
        }
        view.write("Failed by a reason ==> " + message);
    }
}