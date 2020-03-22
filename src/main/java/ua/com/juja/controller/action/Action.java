package ua.com.juja.controller.action;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;
import ua.com.juja.model.ActionMessages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {

    boolean canProcess(String url);

    void get(Service service, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

    default void goToJsp(String jsp, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

    default void doReport(ActionMessages action, String name, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("report", String.format(action.toString(), name));
        goToJsp("report.jsp", req, resp);
    }

    default DatabaseManager getManager(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");

        if (manager != null) {
            return manager;
        } else {
            resp.sendRedirect("connect");
            return DatabaseManager.NULL;
        }
    }
}
