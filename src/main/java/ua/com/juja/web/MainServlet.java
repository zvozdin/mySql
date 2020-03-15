package ua.com.juja.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.com.juja.command.Command;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainServlet extends HttpServlet {

    @Autowired
    private Service service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("manager");

        if (action.startsWith("/connect") || manager == null) {
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
            return;
        }

        if (action.startsWith("/menu") || action.equals("/")) {
            List<Command> commands = service.commands();
            req.setAttribute("commands", commands.toString()
                    .substring(1, commands.toString().length() - 1));
            req.getRequestDispatcher("menu.jsp").forward(req, resp);

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
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length());
    }
}