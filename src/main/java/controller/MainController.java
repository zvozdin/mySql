package controller;

import controller.command.Command;
import controller.command.Exit;
import model.DataSet;
import model.DatabaseManager;
import view.View;

import java.util.List;

public class MainController {

    private View view;
    private DatabaseManager manager;
    private Command[] commands;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{new Exit(view)};
    }

    public void run() {
        connectToDB();
        while (true) {
            view.write("Enter a command or help");
            String command = view.read();
            if (command.equals("list")) {
                doList();
            } else if (command.equals("help")) {
                doHelp();
            } else if (commands[0].canProcess(command)) {
                commands[0].process(command);
            } else if (command.startsWith("find|")) {
                doFind(command);
            } else {
                view.write("Non Existent command ==> " + command);
            }
        }
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        printTableHeader(tableName);
        printValues(tableName);
    }

    private void printTableHeader(String tableName) {
        List<String> columns = manager.getTableColumns(tableName);
        String result = "|";
        for (String name : columns) {
            result += name + "|";
        }
        view.write("========================");
        view.write(result);
        view.write("========================");
    }

    private void printValues(String tableName) {
        List<DataSet> users = manager.getTableData(tableName);
        for (DataSet row : users) {
            List<Object> values = row.getValues();
            String result = "|";
            for (Object element : values) {
                result += element + "|";
            }
            view.write(result);
        }
    }

    private void doList() {
        List tablesNames = manager.getTablesNames();
        view.write(tablesNames.toString());
    }

    private void doHelp() {
        view.write("Existing commands:");
        view.write("\tlist");
        view.write("\t\tto display a list of tables");

        view.write("\thelp");
        view.write("\t\tto display a list of commands");

        view.write("\tfind|tableName");
        view.write("\t\tto retrieve content from the 'tableName'");

        view.write("\texit");
        view.write("\t\tto exit from the programm");
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
