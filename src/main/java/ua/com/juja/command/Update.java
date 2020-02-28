package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.TableGenerator;
import ua.com.juja.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public class Update implements Command {

    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public Update() {

    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.UPDATE.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        Map<String, String> set = new LinkedHashMap<>();
        set.put(data[2], data[3]);

        Map<String, String> where = new LinkedHashMap<>();
        where.put(data[4], data[5]);

        manager.update(tableName, set, where);

        view.write(String.format(
                ActionMessages.UPDATE.toString(), where.values().iterator().next()));
        view.write(new TableGenerator()
                .generateTable(manager.getColumns(tableName), manager.getRows(tableName)));
    }

    @Override
    public void processWeb(DatabaseManager manager, String tableName, HttpServletRequest req, HttpServletResponse resp) {
        String[] data = req.getParameter("columns").split("\\|");

        Map<String, String> set = new LinkedHashMap<>();
        set.put(data[0], data[1]);

        Map<String, String> where = new LinkedHashMap<>();
        where.put(data[2], data[3]);

        tableName = req.getParameter("table");

        manager.update(tableName, set, where);

        req.setAttribute("report", String.format(ActionMessages.UPDATE.toString(), where.values().iterator().next()));
        new Find().processWeb(manager, tableName, req, resp);
    }

    @Override
    public String toString() {
        return "update";
    }
}