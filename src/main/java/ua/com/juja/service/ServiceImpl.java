package ua.com.juja.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.controller.UserActionLog;
import ua.com.juja.model.DatabaseConnectionsRepository;
import ua.com.juja.model.UserActionsRepository;
import ua.com.juja.model.entity.DatabaseConnection;
import ua.com.juja.model.entity.Description;
import ua.com.juja.model.entity.UserAction;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class ServiceImpl implements Service {

    @Autowired
    private UserActionsRepository userActions;

    @Autowired
    private DatabaseConnectionsRepository databaseConnections;

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

    @Override
    public void saveUserAction(String action, String userName, String database) {
        if (userName == null) {
            throw new IllegalArgumentException("User Name can't be null!");
        }
        userActions.saveAction(action, userName, database);
    }

    @Override
    public List<UserActionLog> getAllFor(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User Name can't be null!");
        }

        List<UserAction> actions = new LinkedList<>();
        for (DatabaseConnection connection : databaseConnections.findByUserName(userName)) {
            List<UserAction> userActionList = userActions.findByDatabaseConnectionId(new ObjectId(connection.getId()));
            for (UserAction userAction : userActionList) {
                userAction.setDatabaseConnection(connection);
            }
            actions.addAll(userActionList);
        }

        List<UserActionLog> result = new LinkedList<>();
        for (UserAction action : actions) {
            result.add(new UserActionLog(action));
        }
        return result;
    }
}