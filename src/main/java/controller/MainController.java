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
                new Exit(view),
                new IsConnected(manager, view),
                new Tables(manager, view),
                new Find(manager, view),
                new Unsupported(view)};
    }

    public void run() {
        view.write("Hello, User!");
        view.write("Enter the Database name, Username and Password in the format: 'connect|database|user|password' " +
                "or help");

        while (true) {
            String input = view.read();
            if (input==null) { // null when interrupt application Ctrl F2
                new Exit(view).process(input);
            }
            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
            view.write("Enter a command or help");
        }
    }
}