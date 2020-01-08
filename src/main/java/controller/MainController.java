package controller;

import model.DatabaseManager;
import model.JDBCDatabaseManager;
import view.Console;
import view.View;

public class MainController {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();
        view.write("Hello, User!");
        view.write("Enter the Database name, Username and Password in the format: connect|database|user|password");

        while (true) {
            String input = view.read();
            String[] data = input.split("\\|");
            String database = data[1];
            String user = data[2];
            String password = data[3];
            try {
                manager.connect(database, user, password);
                break;
            } catch (Exception e) {
                String message = "" + e.getMessage();
                if (e.getCause() != null) {
                    message += "\n" + e.getCause().getMessage();
                }
                view.write("Fail for a reason ==> " + message);
                view.write("Try again...");
            }
        }
        view.write("Success!");
    }
}
