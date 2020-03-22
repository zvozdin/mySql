package ua.com.juja.controller.action;

import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TablesAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/tables");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");
        if (manager == null) {
            resp.sendRedirect("connect");
            return;
        }

        List<String> tables = manager.getTables();
        String tablesWithoutBrackets = tables.toString().substring(1, tables.toString().length() - 1);
        req.setAttribute("tables", tablesWithoutBrackets);
        goToJsp("tables.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) {
        // do nothing
    }

    @Override
    public String toString() {
        return "tables";
    }
}