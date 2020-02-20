package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Connect implements Command {

    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public Connect() {
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.CONNECT.toString(), command);

        String[] data = command.split("\\|");
        String database = data[1];
        String user = data[2];
        String password = data[3];

        manager.connect(database, user, password);

        view.write(ActionMessages.SUCCESS.toString());
    }

    @Override
    public void processWeb(DatabaseManager manager, String name, HttpServletRequest req, HttpServletResponse resp)
    {
        String database = req.getParameter("database");
        String user = req.getParameter("user");
        String password = req.getParameter("password");

        manager = new JDBCDatabaseManager();
        manager.connect(database, user, password);

        req.getSession().setAttribute("manager", manager);
    }

    @Override
    public String toString() {
        return "connect";
    }
}