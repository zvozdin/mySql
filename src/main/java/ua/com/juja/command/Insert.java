package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class Insert implements Command {

    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public Insert() {

    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.INSERT.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        Map<String, String> insert = getCommandParameters(data);

        manager.insert(tableName, insert);

        view.write(String.format(ActionMessages.INSERT.toString(), insert.values()));
    }

    @Override
    public void processWeb(DatabaseManager manager, String tableName, HttpServletRequest req, HttpServletResponse resp) {
        String[] split = req.getParameter("columns").split("\\|");
        String[] data = new String[split.length + 2];
        System.arraycopy(split, 0, data, 2, split.length);

        Map<String, String> insert = getCommandParameters(data);
        tableName = req.getParameter("table");

        manager.insert(tableName, insert);

        req.setAttribute("report", String.format(ActionMessages.INSERT.toString(), insert.values()));
        new Find().processWeb(manager, tableName, req, resp);
    }

    @Override
    public String toString() {
        return "insert";
    }
}