package ua.com.juja.web;

import ua.com.juja.command.Command;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;
import ua.com.juja.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
            // todo replaced / and /menu to web.xml into separate Servlet
            List<Command> commands = service.commands();
            req.setAttribute("commands", commands.toString()
                    .substring(1, commands.toString().length() - 1));
            req.getRequestDispatcher("menu.jsp").forward(req, resp);

        } else if (action.startsWith("/tables")) {
            // todo replaced /tables to web.xml into separate Servlet
            List<String> tables = manager.getTables();
            req.setAttribute("report", tables.toString()
                    .substring(1, tables.toString().length() - 1));
            req.getRequestDispatcher("report.jsp").forward(req, resp);

        } else if (action.startsWith("/help")) {
            // TODO in help.jsp make commands as references
            // todo replaced /help to web.xml into separate Servlet
            req.getRequestDispatcher("help.jsp").forward(req, resp);

        } else {
            for (Command command : service.commands()) {
                if (action.startsWith("/" + command.toString())) {
                    req.setAttribute("command", command.toString());
                    req.getRequestDispatcher("setName.jsp").forward(req, resp);
                    return;
                }
            }
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");

        try {
            String action = getAction(req).substring(1);
            String name = req.getParameter(action);

            for (Command command : service.commands()) {
                if (action.equals(command.toString())) {
                    command.processWeb(manager, name, req, resp);
                    if (action.equals("connect")) {
                        resp.sendRedirect("menu");
                        return;
                    }

                    if (action.equals("find") ||
                        action.equals("insert") ||
                        action.equals("update") ||
                        action.equals("delete"))
                    {
                        req.getRequestDispatcher("table.jsp").forward(req, resp);
                        return;
                    }
                    req.getRequestDispatcher("report.jsp").forward(req, resp);
                    return;
                }
            }
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length());
    }
}