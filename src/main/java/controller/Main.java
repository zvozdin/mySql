package controller;

import model.DatabaseManager;
import model.JDBCDatabaseManager;
import view.Console;
import view.View;

public class Main {
    public static void main(String[] args) {
        DatabaseManager manager = new JDBCDatabaseManager();
        View view = new Console();
        MainController controller = new MainController(manager, view);

        controller.run();
    }
}