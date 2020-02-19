package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.TableGenerator;
import ua.com.juja.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Find implements Command {

    private DatabaseManager manager;
    private View view;
    private Service service;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public Find(Service service) {
        this.service = service;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.FIND.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        view.write(new TableGenerator()
                .generateTable(manager.getColumns(tableName), manager.getRows(tableName)));
    }

    @Override
    public void processWeb(DatabaseManager manager, String tableName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("rows", service.find(manager, tableName));
        req.getRequestDispatcher("table.jsp").forward(req, resp);
    }

    @Override
    public String toString() {
        return "find";
    }
}