package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.juja.controller.action.Action;
import ua.com.juja.model.ActionMessages;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.service.Service;

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
    public String menu(Model model) {
        List<Action> commands = service.getActions();
        commands = commands.subList(0, commands.size() - 2);

        model.addAttribute("commands", commands.toString()
                .substring(1, commands.toString().length() - 1));
        return "menu";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session, Model model) {
        String page = (String) session.getAttribute("fromPage");
        session.removeAttribute("fromPage");
        model.addAttribute("connection", new Connection(page));

        if (getManager(session) == null) {
            return "connect";
        } else {
            return "redirect:/menu";
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(@ModelAttribute("connection") Connection connection,
                             ModelMap model, HttpSession session) {
        try {
            DatabaseManager manager = getDatabaseManager();
            manager.connect(connection.getDatabase(), connection.getUser(), connection.getPassword());

            session.setAttribute("manager", manager);
            return "redirect:" + connection.getPage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/tables", manager, session)) return "redirect:/connect";

        model.addAttribute("tables", getTables(manager));
        model.addAttribute("command", "tables");
        return "tables";
    }

    @RequestMapping(value = "/tables/{table}", method = RequestMethod.GET)
    public String table(Model model,
                          @PathVariable(value = "table") String tableName,
                          HttpSession session) {
        DatabaseManager manager = getManager(session);

        model.addAttribute("rows", getRows(manager, tableName));
        return "table";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/clear", manager, session)) return "redirect:/connect";

        model.addAttribute("command", "clear");
        model.addAttribute("tables", getTables(manager));
        return "tables";
    }

    @RequestMapping(value = "/clear/{table}", method = RequestMethod.GET)
    public String clearTable(Model model,
                        @PathVariable(value = "table") String tableName,
                        HttpSession session) {
        DatabaseManager manager = getManager(session);

        manager.clear(tableName);

        model.addAttribute("report", String.format(ActionMessages.CLEAR.toString(), tableName));
        model.addAttribute("rows", getRows(manager, tableName));
        return "table";
    }

    @Lookup
    public DatabaseManager getDatabaseManager() {
        return null;
    }

    private String getTables(DatabaseManager manager) {
        List<String> tablesWithBrackets = manager.getTables();
        return tablesWithBrackets.toString().substring(1, tablesWithBrackets.toString().length() - 1);
    }

    private List<List<String>> getRows(DatabaseManager manager, String tableName) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(new ArrayList<>(manager.getColumns(tableName)));
        rows.addAll(manager.getRows(tableName));
        return rows;
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("manager");
    }

    private boolean managerNull(String fromPage, DatabaseManager manager, HttpSession session) {
        if (manager == null) {
            session.setAttribute("fromPage", fromPage);
            return true;
        }
        return false;
    }
}