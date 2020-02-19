package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;
    private Service service;

    public Clear(Service service) {
        this.service = service;
    }

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.CLEAR.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        manager.clear(tableName);
        view.write(String.format(ActionMessages.CLEAR.toString(), tableName));
    }

    @Override
    public void processWeb(DatabaseManager manager, String tableName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.clear(manager, tableName);
        req.setAttribute("report", String.format(ActionMessages.CLEAR.toString(), tableName));
        req.getRequestDispatcher("report.jsp").forward(req, resp);
    }

    @Override
    public String toString() {
        return "clear";
    }
}