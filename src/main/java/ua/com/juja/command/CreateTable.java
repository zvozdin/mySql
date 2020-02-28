package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CreateTable implements Command {

    private DatabaseManager manager;
    private View view;

    public CreateTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public CreateTable() {

    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.CREATE.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        Set<String> columns = new LinkedHashSet<>();
        for (int index = 2; index < data.length; index++) {
            columns.add(data[index]);
        }

        manager.createTable(tableName, columns);

        view.write(String.format(ActionMessages.CREATE.toString(), tableName));
    }

    @Override
    public void processWeb(DatabaseManager manager, String tableName, HttpServletRequest req, HttpServletResponse resp) {
        tableName = req.getParameter("table");
        Set<String> columns = new LinkedHashSet<>(Arrays.asList(req.getParameter("columns")
                .split("\\|")));
        manager.createTable(tableName, columns);

        req.setAttribute("report", String.format(ActionMessages.CREATE.toString(), tableName));
    }

    @Override
    public String toString() {
        return "newTable";
    }
}