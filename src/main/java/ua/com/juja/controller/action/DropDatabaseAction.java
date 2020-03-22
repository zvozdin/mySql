package ua.com.juja.controller.action;

import ua.com.juja.model.ActionMessages;
import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DropDatabaseAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/dropDatabase");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("command", this.toString());
        goToJsp("setName.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DatabaseManager manager = getManager(req, resp);

        String databaseName = req.getParameter("dropDatabase");
        manager.dropDatabase(databaseName);

        doReport(ActionMessages.DROP_DB, databaseName, req, resp);
    }

    @Override
    public String toString() {
        return "dropDatabase";
    }
}