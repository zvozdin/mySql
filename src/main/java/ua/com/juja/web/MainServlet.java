package ua.com.juja.web;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;
import ua.com.juja.service.ServiceImpl;
import ua.com.juja.view.ActionMessages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    Service service;

    @Override
    public void init() throws ServletException {
        super.init();

        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");

        if (action.startsWith("/connect")) {
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
            return;
        }

        if (manager == null) {
            resp.sendRedirect("connect");
            return;
        }

        if (action.startsWith("/menu") || action.equals("/")) {
            req.setAttribute("commands", service.commands());
            req.getRequestDispatcher("menu.jsp").forward(req, resp);

        } else if (action.startsWith("/help")) {
            // TODO in help.jsp make commands as references
            req.getRequestDispatcher("help.jsp").forward(req, resp);

        } else {
            for (String command : service.commands()) {
                if (action.startsWith("/" + command)) {
                    req.setAttribute("command", command);
                    req.getRequestDispatcher(command == "newDatabase" || command == "dropDatabase"
                            ? "setDatabaseName.jsp"
                            : "setTableName.jsp").forward(req, resp);
                    return;
                }
            }
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");

        try {
            if (action.startsWith("/connect")) {
                String database = req.getParameter("database");
                String user = req.getParameter("user");
                String password = req.getParameter("password");
                manager = service.connect(database, user, password);
                req.getSession().setAttribute("manager", manager);
                resp.sendRedirect("menu");
                return;

            } else if (action.startsWith("/newDatabase") || action.startsWith("/dropDatabase")) {
                req.setAttribute("report", action.startsWith("/newDatabase")
                        ? service.newDatabase(manager, req.getParameter("newDatabase"))
                        : service.dropDatabase(manager, req.getParameter("dropDatabase")));
                req.getRequestDispatcher("report.jsp").forward(req, resp);
                return;

            } else {
                String command = action.substring(1);
                switch (command) {
                    case "find":
                        req.setAttribute("rows", service.find(manager, req.getParameter(command)));
                        req.getRequestDispatcher("table.jsp").forward(req, resp);
                        break;
                    case "clear":
                        service.clear(manager, req.getParameter(command));
                        req.setAttribute(
                                "report", String.format(ActionMessages.CLEAR.toString(), req.getParameter(command)));
                        req.getRequestDispatcher("report.jsp").forward(req, resp);
                        break;
                }
            }
        } catch (
                Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length());
    }
}