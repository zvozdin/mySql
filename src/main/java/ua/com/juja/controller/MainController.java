package ua.com.juja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.UserActionsRepository;
import ua.com.juja.model.resources.ActionMessages;
import ua.com.juja.service.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private Service service;

    @Autowired
    private UserActionsRepository userActions;

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (managerNull("/update", manager, session)) return "redirect:/connect";

        setFormAttributes("Tables", getFormattedData(manager.getTables()), "update", model);
        return "tables";
    }

    @RequestMapping(value = "/update/{name}", method = RequestMethod.GET)
    public String update(Model model,
                         @PathVariable(value = "name") String tableName,
                         HttpSession session) {
        setFormAttributes(tableName, getFormattedData(new LinkedList<>(getManager(session).getColumns(tableName))),
                "update", model);
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Model model,
                         @RequestParam Map<String, String> queryMap,
                         HttpSession session) {
        String tableName = queryMap.get("name");
        queryMap.remove("name");

        Map<String, String> set = new LinkedHashMap<>();
        set.put(queryMap.get("setColumn"), queryMap.get("setValue"));

        Map<String, String> where = new LinkedHashMap<>();
        where.put(queryMap.get("whereColumn"), queryMap.get("whereValue"));

        DatabaseManager manager = getManager(session);
        manager.update(tableName, set, where);
        userActions.saveAction(String.format("Update in %s", tableName), manager.getUserName(), manager.getDatabaseName());

        setTableAttributes(ActionMessages.UPDATE, where.toString(), tableName, manager, model);
        return "table";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);
        if (managerNull("/delete", manager, session)) return "redirect:/connect";

        setFormAttributes("Tables", getFormattedData(manager.getTables()), "delete", model);
        return "tables";
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.GET)
    public String delete(Model model,
                         @PathVariable(value = "name") String tableName,
                         HttpSession session) {
        setFormAttributes(tableName, getFormattedData(new LinkedList<>(getManager(session).getColumns(tableName))),
                "delete", model);
        return "delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(Model model,
                         @RequestParam Map<String, String> queryMap,
                         HttpSession session) {
        String tableName = queryMap.get("name");
        queryMap.remove("name");

        Map<String, String> delete = new LinkedHashMap<>();
        delete.put(queryMap.get("deleteColumn"), queryMap.get("deleteValue"));

        DatabaseManager manager = getManager(session);
        manager.deleteRow(tableName, delete);
        userActions.saveAction(String.format("DeleteRow in %s", tableName), manager.getUserName(), manager.getDatabaseName());

        setTableAttributes(ActionMessages.DELETE, delete.toString(), tableName, manager, model);
        return "table";
    }

    private String getFormattedData(List<String> data) {
        return data.toString().replace("[", "").replace("]", "");
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

    private void setFormAttributes(String head, String tableData, String command, Model model) {
        model.addAttribute("head", head);
        model.addAttribute("tableData", tableData);
        model.addAttribute("command", command);
    }

    private void setTableAttributes(ActionMessages action, String element, String tableName, DatabaseManager manager, Model model) {
        model.addAttribute("report", String.format(action.toString(), element));
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