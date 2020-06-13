package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.dao.DatabaseManager;
import ua.com.juja.dao.Action;
import ua.com.juja.service.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class RestService {

    @Autowired
    private Service service;

    private String database;
    private String user;

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<String> menuItems() {
        return service.getActions();
    }

    @RequestMapping(value = "/help/content", method = RequestMethod.GET)
    public List<Action> helpItems() {
        return service.getActionsDescription();
    }

    @RequestMapping(value = "/connected", method = RequestMethod.GET)
    public String isConnected(HttpSession session) {
        DatabaseManager manager = getManager(session);
        return (manager != null) ? manager.getUserName() : null;
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(@ModelAttribute Connection connection, HttpSession session) {
        try {
            database = connection.getDatabase();
            user = connection.getUser();
            session.setAttribute("manager", service.connect(database, user, connection.getUser()));
            service.saveUserAction("CONNECT", user, database);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/tables/content", method = RequestMethod.GET)
    public List<String> tables(HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        service.saveUserAction("Tables", user, database);
        return manager.getTables();
    }

    @RequestMapping(value = "/tables/{table}/content", method = RequestMethod.GET)
    public List<List<String>> table(@PathVariable(value = "table") String tableName, HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        service.saveUserAction(String.format("View Table(%s)", tableName), user, database);
        return getRows(manager, tableName);
    }

    @RequestMapping(value = "/newDatabase/{name}", method = RequestMethod.PUT)
    public String newDatabase(@PathVariable(value = "name") String databaseName, HttpSession session) {
        try {
            getManager(session).createDatabase(databaseName);
            service.saveUserAction(String.format("NewDatabase(%s)", databaseName), user, database);
            return String.format(ActionMessages.DATABASE_NEW.toString(), databaseName);
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(ActionMessages.DATABASE_EXISTS.toString(), databaseName);
        }
    }

    @RequestMapping(value = "/dropDatabase/content", method = RequestMethod.GET)
    public List<String> databases(HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        return manager.getDatabases();
    }

    @RequestMapping(value = "/dropDatabase/{name}", method = RequestMethod.DELETE)
    public String dropDatabase(@PathVariable(value = "name") String databaseName, HttpSession session) {
        getManager(session).dropDatabase(databaseName);
        service.saveUserAction(String.format("DropDatabase(%s)", databaseName), user, database);
        return String.format(ActionMessages.DROP_DB.toString(), databaseName);
    }

    @RequestMapping(value = "/newTable", method = RequestMethod.POST)
    public String newTable(@RequestParam Map<String, String> queryMap, HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        try {
            getManager(session).createTable(tableName, new LinkedHashSet(queryMap.values()));
            service.saveUserAction(String.format("NewTable(%s)", tableName), user, database);
            return String.format(ActionMessages.CREATE.toString(), tableName);
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(ActionMessages.CREATE_EXISTING_TABLE.toString(), tableName);
        }
    }

    @RequestMapping(value = "/dropTable/{tableName}", method = RequestMethod.DELETE)
    public String dropTable(@PathVariable(value = "tableName") String tableName, HttpSession session) {
        getManager(session).dropTable(tableName);
        service.saveUserAction(String.format("DropTable(%s)", tableName), user, database);
        return String.format(ActionMessages.DROP.toString(), tableName);
    }

    @RequestMapping(value = {"" +
            "/insert/{tableName}/content",
            "/update/{tableName}/content",
            "/delete/{tableName}/content"}, method = RequestMethod.GET)
    public Set<String> getContent(@PathVariable(value = "tableName") String tableName, HttpSession session) {
        return getManager(session).getColumns(tableName);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestParam Map<String, String> queryMap, HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        getManager(session).insert(tableName, queryMap);
        service.saveUserAction(String.format("Insert into %s", tableName), user, database);
        return String.format(ActionMessages.INSERT.toString(), queryMap.toString());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam Map<String, String> queryMap, HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        Map<String, String> set = new LinkedHashMap<>();
        set.put(queryMap.get("setColumn"), queryMap.get("setValue"));

        Map<String, String> where = new LinkedHashMap<>();
        where.put(queryMap.get("whereColumn"), queryMap.get("whereValue"));

        getManager(session).update(tableName, set, where);
        service.saveUserAction(String.format("Update in %s", tableName), user, database);
        return String.format(ActionMessages.UPDATE.toString(), where.toString());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam Map<String, String> queryMap, HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        Map<String, String> delete = new LinkedHashMap<>();
        delete.put(queryMap.get("deleteColumn"), queryMap.get("deleteValue"));

        getManager(session).deleteRow(tableName, delete);
        service.saveUserAction(String.format("DeleteRow in %s", tableName), user, database);
        return String.format(ActionMessages.DELETE.toString(), delete.toString());
    }

    @RequestMapping(value = "/clear/{name}", method = RequestMethod.DELETE)
    public String clear(@PathVariable(value = "name") String tableName, HttpSession session) {
        getManager(session).clear(tableName);
        service.saveUserAction(String.format("Clear(%s)", tableName), user, database);
        return String.format(ActionMessages.CLEAR.toString(), tableName);
    }

    @RequestMapping(value = "/actions/{userName}/content", method = RequestMethod.GET)
    public List<UserActionLog> actions(@PathVariable(value = "userName") String userName) {
        return service.getAllFor(userName);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void connecting(HttpSession session) {
        session.removeAttribute("manager");
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("manager");
    }

    private List<List<String>> getRows(DatabaseManager manager, String tableName) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(new ArrayList<>(manager.getColumns(tableName)));
        rows.addAll(manager.getRows(tableName));
        return rows;
    }
}