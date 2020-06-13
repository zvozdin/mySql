package ua.com.juja.service;

import ua.com.juja.controller.UserActionLog;
import ua.com.juja.dao.DatabaseManager;
import ua.com.juja.dao.Description;

import java.util.List;

public interface Service {

    List<String> getCommands();

    List<Description> getCommandsDescription();

    List<UserActionLog> getAllFor(String userName);

    DatabaseManager connect(String database, String user, String password);

    void saveUserAction(String action, String user, String database);
}