package ua.com.juja.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import ua.com.juja.config.LinkedProperties;
import ua.com.juja.controller.UserActionLog;
import ua.com.juja.dao.DatabaseConnectionsRepository;
import ua.com.juja.dao.DatabaseManager;
import ua.com.juja.dao.UserActionsRepository;
import ua.com.juja.entity.DatabaseConnection;
import ua.com.juja.dao.Action;
import ua.com.juja.entity.UserAction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Component
public class ServiceImpl implements Service {

    private DatabaseManager manager;

    @Autowired
    private UserActionsRepository userActions;

    @Autowired
    private DatabaseConnectionsRepository databaseConnections;

    @Override
    public List<String> getActions() {
        return new LinkedList(getProperties().stringPropertyNames());
    }

    @Override
    public List<Action> getActionsDescription() {
        Properties properties = getProperties();

        List names = new LinkedList(properties.stringPropertyNames());
        names.remove("help");

        List<Action> actions = new LinkedList();
        for (Object name : names) {
            actions.add(new Action(name.toString(), properties.getProperty(name.toString())));
        }

        return actions;
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

    @Override
    public DatabaseManager connect(String database, String user, String password) {
        manager = getDatabaseManager();
        manager.connect(database, user, password);
        return manager;
    }

    @Lookup
    public DatabaseManager getDatabaseManager() {
        return null;
    }

    private Properties getProperties() {
        Properties properties = new LinkedProperties();
        try (FileInputStream inputStream = new FileInputStream("src\\main\\resources\\commandsDescription")) {
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read " + properties + " resource file: " + e);
        }
    }
}