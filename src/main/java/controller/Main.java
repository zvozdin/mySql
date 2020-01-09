package controller;

import model.DatabaseManager;
import model.JDBCDatabaseManager;
import view.Console;
import view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();
        MainController controller = new MainController(view, manager);

        controller.run();
    }
}
