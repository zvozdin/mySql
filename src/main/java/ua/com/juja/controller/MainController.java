package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.juja.controller.action.Action;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private Service service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "redirect:/menu";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(HttpServletRequest request) {
        List<Action> commands = service.getActions();
        commands = commands.subList(0, commands.size() - 2);

        request.setAttribute("commands", commands.toString()
                .substring(1, commands.toString().length() - 1));
        return "menu";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session) {
        if (getManager(session) == null) {
            return "connect";
        } else {
            return "redirect:/menu";
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(HttpServletRequest request, HttpSession session) {
        try {
            String database = request.getParameter("database");
            String user = request.getParameter("user");
            String password = request.getParameter("password");

            DatabaseManager manager = getDatabaseManager();
            manager.connect(database, user, password);

            session.setAttribute("manager", manager);
            return "redirect:/menu";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(HttpSession session, HttpServletRequest request) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return "redirect:/connect";
        }

        List<String> tables = manager.getTables();
        String tablesWithoutBrackets = tables.toString().substring(1, tables.toString().length() - 1);
        request.setAttribute("tables", tablesWithoutBrackets);
        return "tables";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(HttpServletRequest request, HttpSession session) {
        if (getManager(session) == null) {
            return "redirect:/connect";
        }

        request.setAttribute("command", "find");
        return "setName";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String finding(HttpServletRequest request, HttpSession session) {
        DatabaseManager manager = getManager(session);
        String tableName = request.getParameter("find");

        List<List<String>> rows = new ArrayList<>();
        rows.add(new ArrayList<>(manager.getColumns(tableName)));
        rows.addAll(manager.getRows(tableName));

        request.setAttribute("rows", rows);
        return "table";
    }

    @Lookup
    public DatabaseManager getDatabaseManager() {
        return null;
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("manager");
    }
}