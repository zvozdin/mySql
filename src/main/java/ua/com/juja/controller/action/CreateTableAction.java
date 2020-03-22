package ua.com.juja.controller.action;

import ua.com.juja.model.ActionMessages;
import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class CreateTableAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return url.equals("/newTable");
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
        Set<String> columns = new LinkedHashSet<>(Arrays.asList(req.getParameter("columns")
                .split("\\|")));
        manager.createTable(tableName, columns);

        doReport(ActionMessages.CREATE, tableName, req, resp);
    }

    @Override
    public String toString() {
        return "newTable";
    }
}