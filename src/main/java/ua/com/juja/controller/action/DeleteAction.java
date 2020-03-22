package ua.com.juja.controller.action;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;
import ua.com.juja.model.ActionMessages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeleteAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/delete");
    }

    @Override
    public void get(Service service, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("command", this.toString());
        goToJsp("setName.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DatabaseManager manager = getManager(req, resp);

        String tableName = req.getParameter("table");
        String[] split = req.getParameter("columns").split("\\|");

        Map<String, String> delete = getCommandParameters(split);
        manager.deleteRow(tableName, delete);

        doReport(ActionMessages.DELETE, delete.values().iterator().next(), req, resp);
    }

    private Map<String, String> getCommandParameters(String[] split) {
        String[] data = new String[split.length + 2];
        System.arraycopy(split, 0, data, 2, split.length);
        Map<String, String> command = new LinkedHashMap<>();
        for (int index = 1; index < data.length / 2; index++) {
            String column = data[index * 2];
            String value = data[index * 2 + 1];
            command.put(column, value);
        }
        return command;
    }

    @Override
    public String toString() {
        return "delete";
    }
}