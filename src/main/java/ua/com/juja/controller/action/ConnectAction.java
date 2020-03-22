package ua.com.juja.controller.action;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ConnectAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/connect");
    }

    @Override
    public void get(Service service, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        goToJsp("connect.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String database = req.getParameter("database");
        String user = req.getParameter("user");
        String password = req.getParameter("password");

        DatabaseManager manager = getDatabaseManager();
        manager.connect(database, user, password);

        req.getSession().setAttribute("manager", manager);
        resp.sendRedirect("menu");
    }

    @Lookup
    public DatabaseManager getDatabaseManager() {
        return null;
    }

    @Override
    public String toString() {
        return "connect";
    }
}