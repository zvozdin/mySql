package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.juja.controller.action.Action;
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
        if (manager == null) {
            session.setAttribute("fromPage", "/tables");
            return "redirect:/connect";
        }

        List<String> tables = manager.getTables();
        String tablesWithoutBrackets = tables.toString().substring(1, tables.toString().length() - 1);
        model.addAttribute("tables", tablesWithoutBrackets);
        return "tables";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(HttpSession session) {
        if (getManager(session) == null) {
            session.setAttribute("fromPage", "/find");
            return "redirect:/connect";
        }

        session.setAttribute("command", "find");
        return "setName";
    }

    @RequestMapping(value = "/find", params = {"table"}, method = RequestMethod.POST)
    public String finding(Model model,
                          @RequestParam(value = "table") String tableName,
                          HttpSession session) {
        DatabaseManager manager = getManager(session);

        List<List<String>> rows = new ArrayList<>();
        rows.add(new ArrayList<>(manager.getColumns(tableName)));
        rows.addAll(manager.getRows(tableName));

        model.addAttribute("rows", rows);
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