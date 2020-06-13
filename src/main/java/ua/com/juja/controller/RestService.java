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

    @GetMapping(value = "/menu/content")
    public List<String> menuItems() {
        return service.getActions();
    }

    @GetMapping(value = "/help/content")
    public List<Action> helpItems() {
        return service.getActionsDescription();
    }

    @GetMapping(value = "/connected")
    public String isConnected(HttpSession session) {
        DatabaseManager manager = getManager(session);
        return (manager != null) ? manager.getUserName() : null;
    }

    @PostMapping(value = "/connect")
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

    @GetMapping(value = "/tables/content")
    public List<String> tables(HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        service.saveUserAction("Tables", user, database);
        return manager.getTables();
    }

    @GetMapping(value = "/tables/{table}/content")
    public List<List<String>> table(@PathVariable(value = "table") String tableName, HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        service.saveUserAction(String.format("View Table(%s)", tableName), user, database);
        return getRows(manager, tableName);
    }

    @PutMapping(value = "/newDatabase/{name}")
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

    @GetMapping(value = "/dropDatabase/content")
    public List<String> databases(HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        return manager.getDatabases();
    }

    @DeleteMapping(value = "/dropDatabase/{name}")
    public String dropDatabase(@PathVariable(value = "name") String databaseName, HttpSession session) {
        getManager(session).dropDatabase(databaseName);
        service.saveUserAction(String.format("DropDatabase(%s)", databaseName), user, database);
        return String.format(ActionMessages.DROP_DB.toString(), databaseName);
    }

    @PostMapping(value = "/newTable")
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

    @DeleteMapping(value = "/dropTable/{tableName}")
    public String dropTable(@PathVariable(value = "tableName") String tableName, HttpSession session) {
        getManager(session).dropTable(tableName);
        service.saveUserAction(String.format("DropTable(%s)", tableName), user, database);
        return String.format(ActionMessages.DROP.toString(), tableName);
    }

    @GetMapping(value = {"" +
            "/insert/{tableName}/content",
            "/update/{tableName}/content",
            "/delete/{tableName}/content"})
    public Set<String> getContent(@PathVariable(value = "tableName") String tableName, HttpSession session) {
        return getManager(session).getColumns(tableName);
    }

    @PostMapping(value = "/insert")
    public String insert(@RequestParam Map<String, String> queryMap, HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        getManager(session).insert(tableName, queryMap);
        service.saveUserAction(String.format("Insert into %s", tableName), user, database);
        return String.format(ActionMessages.INSERT.toString(), queryMap.toString());
    }

    @PostMapping(value = "/update")
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

    @PostMapping(value = "/delete")
    public String delete(@RequestParam Map<String, String> queryMap, HttpSession session) {
        String tableName = queryMap.get("tableName");
        queryMap.remove("tableName");

        Map<String, String> delete = new LinkedHashMap<>();
        delete.put(queryMap.get("deleteColumn"), queryMap.get("deleteValue"));

        getManager(session).deleteRow(tableName, delete);
        service.saveUserAction(String.format("DeleteRow in %s", tableName), user, database);
        return String.format(ActionMessages.DELETE.toString(), delete.toString());
    }

    @DeleteMapping(value = "/clear/{name}")
    public String clear(@PathVariable(value = "name") String tableName, HttpSession session) {
        getManager(session).clear(tableName);
        service.saveUserAction(String.format("Clear(%s)", tableName), user, database);
        return String.format(ActionMessages.CLEAR.toString(), tableName);
    }

    @GetMapping(value = "/actions/{userName}/content")
    public List<UserActionLog> actions(@PathVariable(value = "userName") String userName) {
        return service.getAllFor(userName);
    }

    @GetMapping(value = "/logout")
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