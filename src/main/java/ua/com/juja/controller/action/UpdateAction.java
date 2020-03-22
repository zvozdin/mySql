package ua.com.juja.controller.action;

import ua.com.juja.model.ActionMessages;
import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/update");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("command", this.toString());
        goToJsp("setName.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DatabaseManager manager = getManager(req, resp);

        String tableName = req.getParameter("table");
        String[] data = req.getParameter("columns").split("\\|");

        Map<String, String> set = new LinkedHashMap<>();
        set.put(data[0], data[1]);

        Map<String, String> where = new LinkedHashMap<>();
        where.put(data[2], data[3]);

        manager.update(tableName, set, where);

        doReport(ActionMessages.UPDATE, where.values().iterator().next(), req, resp);
    }

    @Override
    public String toString() {
        return "update";
    }
}