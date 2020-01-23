package ua.com.juja;

import ua.com.juja.controller.MainController;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;
import ua.com.juja.view.Console;
import ua.com.juja.view.View;

public class Main {
    public static void main(String[] args) {
        DatabaseManager manager = new JDBCDatabaseManager();
        View view = new Console();
        MainController controller = new MainController(manager, view);

        controller.run();
    }
}