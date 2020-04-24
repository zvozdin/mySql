package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.entity.Description;
import ua.com.juja.service.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class RestService {

    @Autowired
    private Service service;

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<String> menuItems() {
        return service.getCommands();
    }

    @RequestMapping(value = "/help/content", method = RequestMethod.GET)
    public List<Description> helpItems() {
        return service.getCommandsDescription();
    }

    @RequestMapping(value = "/tables/content", method = RequestMethod.GET)
    public List<String> tables(HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        return manager.getTables();
    }

    @RequestMapping(value = "/tables/{table}/content", method = RequestMethod.GET)
    public List<List<String>> table(@PathVariable(value = "table") String tableName, HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            return new LinkedList<>();
        }
        return getRows(manager, tableName);
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