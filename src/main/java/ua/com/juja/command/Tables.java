package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Tables implements Command {

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public Tables() {

    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        view.write(manager.getTables().toString());
    }

    @Override
    public void processWeb(DatabaseManager manager, String name, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> tables = manager.getTables();
        req.setAttribute("report", tables.toString()
                .substring(1, tables.toString().length() - 1));
    }

    @Override
    public String toString() {
        return "tables";
    }
}