package ua.com.juja.controller.action;

import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/find");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("command", this.toString());
        goToJsp("setName.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");

        if (manager != null) {
            String tableName = req.getParameter("table");
            List<List<String>> rows = new ArrayList<>();
            rows.add(new ArrayList<>(manager.getColumns(tableName)));
            rows.addAll(manager.getRows(tableName));

            req.setAttribute("rows", rows);
            goToJsp("table.jsp", req, resp);
        } else {
            resp.sendRedirect("connect");
        }
    }

    @Override
    public String toString() {
        return "find";
    }
}