package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.TableGenerator;
import ua.com.juja.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DeleteRow implements Command {

    private DatabaseManager manager;
    private View view;

    public DeleteRow(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public DeleteRow() {

    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.DELETE.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        Map<String, String> delete = getCommandParameters(data);

        manager.deleteRow(tableName, delete);

        view.write(String.format(ActionMessages.DELETE.toString(), delete.values().iterator().next()));
        view.write(new TableGenerator()
                .generateTable(manager.getColumns(tableName), manager.getRows(tableName)));
    }

    @Override
    public void processWeb(DatabaseManager manager, String tableName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] split = req.getParameter("columns").split("\\|");
        String[] data = new String[split.length + 2];
        System.arraycopy(split, 0, data, 2, split.length);

        Map<String, String> delete = getCommandParameters(data);
        tableName = req.getParameter("table");

        manager.deleteRow(tableName, delete);

        req.setAttribute("report", String.format(ActionMessages.DELETE.toString(), delete.values().iterator().next()));
        new Find().processWeb(manager, tableName, req, resp);
    }

    @Override
    public String toString() {
        return "delete";
    }
}