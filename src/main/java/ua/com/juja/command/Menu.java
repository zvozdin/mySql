package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Menu implements Command {
    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }

    @Override
    public void processWeb(DatabaseManager manager, String name, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public String toString() {
        return "menu";
    }
}