package ua.com.juja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.controller.UserActionLog;
import ua.com.juja.model.UserActionsRepository;
import ua.com.juja.model.entity.Description;
import ua.com.juja.model.entity.UserAction;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class ServiceImpl implements Service {

    @Autowired
    private UserActionsRepository userActions;

    @Override
    public List<String> getCommands() {
        return Arrays.asList(
                "help",
                "connect",
                "newDatabase",
                "dropDatabase",
                "tables",
                "newTable",
                "dropTable",
                "insert",
                "update",
                "delete",
                "clear",
                "actions"
        );
    }

    @Override
    public List<Description> getCommandsDescription() {
        return Arrays.asList(
                new Description("connect", "to connect to the database"),
                new Description("newDatabase", "to create a new database"),
                new Description("dropDatabase", "to delete the database"),
                new Description("tables", "to display a list of tables"),
                new Description("newTable", "to create a new table"),
                new Description("dropTable", "to delete the table"),
                new Description("insert", "to record content to the 'tableName'"),
                new Description("update",
                        "to update the content in the 'tableName' " +
                                "set column1 = value1 where column2 = value2"),
                new Description("delete", "to delete content where column = value"),
                new Description("clear", "to delete content from the 'tableName'")
        );
    }

    // TODO add userActions.saveAction(String.format("DeleteRow in %s", tableName), user, database);

    @Override
    public List<UserActionLog> getAllFor(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User Name can't be null!");
        }

        List<UserActionLog> result = new LinkedList<>();
        for (UserAction action : userActions.findByUserName(userName)) {
            result.add(new UserActionLog(action));
        }
        return result;
    }
}