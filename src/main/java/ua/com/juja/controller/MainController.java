package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ua.com.juja.model.ActionMessages;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.UserActionsDao;
import ua.com.juja.service.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private Service service;
    @Autowired
    private UserActionsDao userActionsDao;

    private Connection connection;
    private String user;
    private String database;

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
        model.addAttribute("commands", service.getCommands());
        return "menu";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session, Model model) {
        String page = (String) session.getAttribute("fromPage");
        session.removeAttribute("fromPage");

        model.addAttribute("connection", new Connection(page));
        return "connect";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(@ModelAttribute("connection") Connection connection,
                             Model model, HttpSession session) {
        this.connection = connection;
        this.user = connection.getUser();
        this.database = connection.getDatabase();

        try {
            DatabaseManager manager = getDatabaseManager();
            manager.connect(database, user, connection.getPassword());
            userActionsDao.log(user, database, "CONNECT");

            session.setAttribute("manager", manager);
            return "redirect:" + connection.getPage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/newDatabase", method = RequestMethod.GET)
    public String newDatabase(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/newDatabase", manager, session)) return "redirect:/connect";

        model.addAttribute("command", "newDatabase");
        return "setName";
    }

    @RequestMapping(value = "/newDatabase", params = {"name"}, method = RequestMethod.GET)
    public String newDatabase(Model model,
                              @RequestParam(value = "name") String databaseName,
                              HttpSession session) {
        try {
            getManager(session).createDatabase(databaseName);
            userActionsDao.log(user, database, String.format("NewDatabase(%s)", databaseName));

            model.addAttribute("report", String.format(ActionMessages.DATABASE_NEW.toString(), databaseName));
            return "report";
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

        setFormAttributes("Databases", manager.getDatabases(), "dropDatabase", model);
        return "tables";
    }

    @RequestMapping(value = "/dropDatabase/{name}", method = RequestMethod.GET)
    public String dropDatabase(Model model,
                               @PathVariable(value = "name") String databaseName,
                               HttpSession session) {
        getManager(session).dropDatabase(databaseName);
        userActionsDao.log(user, database, String.format("DropDatabase(%s)", databaseName));

        model.addAttribute("report", String.format(ActionMessages.DROP_DB.toString(), databaseName));
        return "report";
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/tables", manager, session)) return "redirect:/connect";

        userActionsDao.log(user, database, "Tables");

        setFormAttributes("Tables", manager.getTables(), "tables", model);
        return "tables";
    }

    @RequestMapping(value = "/tables/{name}", method = RequestMethod.GET)
    public String table(Model model,
                        @PathVariable(value = "name") String tableName,
                        HttpSession session) {
        userActionsDao.log(user, database, String.format("Table(%s)", tableName));

        model.addAttribute("rows", getRows(getManager(session), tableName));
        return "table";
    }

    @RequestMapping(value = "/newTable", method = RequestMethod.GET)
    public String newTable(HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/newTable", manager, session)) return "redirect:/connect";
        return "setName";
    }

    @RequestMapping(value = "/newTable", params = {"name", "count"}, method = RequestMethod.GET)
    public String newTable(Model model,
                           @RequestParam(value = "name") String tableName,
                           @RequestParam(value = "count") String count) {
        model.addAttribute("name", tableName);
        model.addAttribute("count", count);
        return "createTable";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/newTable", method = RequestMethod.POST)
    public String newTable(Model model,
                           @RequestParam Map<String, String> queryMap,
                           HttpSession session) {
        try {
            String tableName = queryMap.get("name");
            queryMap.remove("name");

            getManager(session).createTable(tableName, new LinkedHashSet(new LinkedList(queryMap.values())));
            userActionsDao.log(user, database, String.format("NewTable(%s)", tableName));

            model.addAttribute("report", String.format(ActionMessages.CREATE.toString(), tableName));
            return "report";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/dropTable", method = RequestMethod.GET)
    public String dropTable(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/dropTable", manager, session)) return "redirect:/connect";

        setFormAttributes("Tables", manager.getTables(), "dropTable", model);
        return "tables";
    }

    @RequestMapping(value = "/dropTable/{name}", method = RequestMethod.GET)
    public String dropTable(Model model,
                            @PathVariable(value = "name") String tableName,
                            HttpSession session) {
        getManager(session).dropTable(tableName);
        userActionsDao.log(user, database, String.format("DropTable(%s)", tableName));

        model.addAttribute("report", String.format(ActionMessages.DROP.toString(), tableName));
        return "report";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/insert", manager, session)) return "redirect:/connect";

        setFormAttributes("Tables", manager.getTables(), "insert", model);
        return "tables";
    }

    @RequestMapping(value = "/insert/{tableName}", method = RequestMethod.GET)
    public String insert(Model model,
                         @PathVariable(value = "tableName") String tableName,
                         HttpSession session) {

        setFormAttributes(tableName, new ArrayList<>(getManager(session).getColumns(tableName)),
                "insert", model);
        return "insert";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(Model model,
                         @RequestParam Map<String, String> queryMap,
                         HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        DatabaseManager manager = getManager(session);
        manager.insert(tableName, queryMap);
        userActionsDao.log(user, database, String.format("Insert into %s", tableName));

        setTableAttributes(ActionMessages.INSERT, queryMap.toString(), tableName, manager, model);
        return "table";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/update", manager, session)) return "redirect:/connect";

        setFormAttributes("Tables", manager.getTables(), "update", model);
        return "tables";
    }

    @RequestMapping(value = "/update/{tableName}", method = RequestMethod.GET)
    public String update(Model model,
                         @PathVariable(value = "tableName") String tableName,
                         HttpSession session) {

        setFormAttributes(tableName, new LinkedList<>(getManager(session).getColumns(tableName)),
                "update", model);
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Model model,
                         @RequestParam Map<String, String> queryMap,
                         HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        Map<String, String> set = new LinkedHashMap<>();
        set.put(queryMap.get("setColumn"), queryMap.get("setValue"));

        Map<String, String> where = new LinkedHashMap<>();
        where.put(queryMap.get("whereColumn"), queryMap.get("whereValue"));

        DatabaseManager manager = getManager(session);
        manager.update(tableName, set, where);
        userActionsDao.log(user, database, String.format("Update in %s", tableName));

        setTableAttributes(ActionMessages.UPDATE, where.toString(), tableName, manager, model);
        return "table";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/delete", manager, session)) return "redirect:/connect";

        setFormAttributes("Tables", manager.getTables(), "delete", model);
        return "tables";
    }

    @RequestMapping(value = "/delete/{tableName}", method = RequestMethod.GET)
    public String delete(Model model,
                         @PathVariable(value = "tableName") String tableName,
                         HttpSession session) {

        setFormAttributes(tableName, new LinkedList<>(getManager(session).getColumns(tableName)),
                "delete", model);
        return "delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(Model model,
                         @RequestParam Map<String, String> queryMap,
                         HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        Map<String, String> delete = new LinkedHashMap<>();
        delete.put(queryMap.get("deleteColumn"), queryMap.get("deleteValue"));

        DatabaseManager manager = getManager(session);
        manager.deleteRow(tableName, delete);
        userActionsDao.log(user, database, String.format("DeleteRow in %s", tableName));

        setTableAttributes(ActionMessages.DELETE, delete.toString(), tableName, manager, model);
        return "table";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (managerNull("/clear", manager, session)) return "redirect:/connect";

        setFormAttributes("Tables", manager.getTables(), "clear", model);
        return "tables";
    }

    @RequestMapping(value = "/clear/{name}", method = RequestMethod.GET)
    public String clear(Model model,
                        @PathVariable(value = "name") String tableName,
                        HttpSession session) {
        DatabaseManager manager = getManager(session);
        manager.clear(tableName);
        userActionsDao.log(user, database, String.format("Clear(%s)", tableName));

        setTableAttributes(ActionMessages.CLEAR, tableName, tableName, manager, model);
        return "table";
    }

    @RequestMapping(value = "/actions/{userName}", method = RequestMethod.GET)
    public String actions(Model model,
                        @PathVariable(value = "userName") String userName,
                        HttpSession session) {
        model.addAttribute("actions", userActionsDao.getAllFor(userName));
        return "actions";
    }

    @Lookup
    public DatabaseManager getDatabaseManager() {
        return null;
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

    private void setFormAttributes(String head, List<String> tableData, String command, Model model) {
        model.addAttribute("head", head);
        model.addAttribute("tableData", tableData);
        model.addAttribute("command", command);
    }

    private void setTableAttributes(ActionMessages insert, String s, String tableName, DatabaseManager manager, Model model) {
        model.addAttribute("report", String.format(insert.toString(), s));
        model.addAttribute("rows", getRows(manager, tableName));
    }

    private boolean managerNull(String fromPage, DatabaseManager manager, HttpSession session) {
        if (manager == null) {
            session.setAttribute("fromPage", fromPage);
            return true;
        }
        return false;
    }
}