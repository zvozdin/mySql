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
        List<String> commandsListWithBrackets = service.getActions();
        String commands = commandsListWithBrackets.toString()
                .substring(1, commandsListWithBrackets.toString().length() - 1);

        model.addAttribute("commands", commands);
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

    @RequestMapping(value = "/dropDatabase", method = RequestMethod.GET)
    public String dropDatabase(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/dropDatabase", manager, session)) return "redirect:/connect";

        setAttributes("Databases", getFormattedTable(manager.getDatabases()), "dropDatabase", model);
        return "tables";
    }

    @RequestMapping(value = "/dropDatabase/{name}", method = RequestMethod.GET)
    public String dropDatabase(Model model,
                        @PathVariable(value = "name") String databaseName,
                        HttpSession session) {
        DatabaseManager manager = getManager(session);

        manager.dropDatabase(databaseName);

        model.addAttribute("report", String.format(ActionMessages.DROP_DB.toString(), databaseName));
        return "report";
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/tables", manager, session)) return "redirect:/connect";

        setAttributes("Tables", getFormattedTable(manager.getTables()), "tables", model);
        return "tables";
    }

    @RequestMapping(value = "/tables/{name}", method = RequestMethod.GET)
    public String table(Model model,
                        @PathVariable(value = "name") String tableName,
                        HttpSession session) {
        DatabaseManager manager = getManager(session);

        model.addAttribute("rows", getRows(manager, tableName));
        return "table";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/clear", manager, session)) return "redirect:/connect";

        setAttributes("Tables", getFormattedTable(manager.getTables()), "clear", model);
        return "tables";
    }

    @RequestMapping(value = "/clear/{name}", method = RequestMethod.GET)
    public String clear(Model model,
                        @PathVariable(value = "name") String tableName,
                        HttpSession session) {
        DatabaseManager manager = getManager(session);

        manager.clear(tableName);

        model.addAttribute("report", String.format(ActionMessages.CLEAR.toString(), tableName));
        model.addAttribute("rows", getRows(manager, tableName));
        return "table";
    }

    @RequestMapping(value = "/dropTable", method = RequestMethod.GET)
    public String dropTable(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/dropTable", manager, session)) return "redirect:/connect";

        setAttributes("Tables", getFormattedTable(manager.getTables()), "dropTable", model);
        return "tables";
    }

    @RequestMapping(value = "/dropTable/{name}", method = RequestMethod.GET)
    public String dropTable(Model model,
                            @PathVariable(value = "name") String tableName,
                            HttpSession session) {
        DatabaseManager manager = getManager(session);

        manager.dropTable(tableName);

        model.addAttribute("report", String.format(ActionMessages.DROP.toString(), tableName));
        return "report";
    }

    @Lookup
    public DatabaseManager getDatabaseManager() {
        return null;
    }

    private String getFormattedTable(List<String> data) {
        return data.toString().substring(1, data.toString().length() - 1);
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

    private void setAttributes(String head, String tableData, String command, Model model) {
        model.addAttribute("head", head);
        model.addAttribute("tables", tableData);
        model.addAttribute("command", command);
    }

    private boolean managerNull(String fromPage, DatabaseManager manager, HttpSession session) {
        if (manager == null) {
            session.setAttribute("fromPage", fromPage);
            return true;
        }
        return false;
    }
}