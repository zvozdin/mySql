package ua.com.juja.web;

import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TablesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");
        if (manager == null) {
            resp.sendRedirect("connect");
            return;
        }

        List<String> tables = manager.getTables();
        req.setAttribute("report", tables.toString()
                .substring(1, tables.toString().length() - 1));
        req.getRequestDispatcher("report.jsp").forward(req, resp);
    }
}