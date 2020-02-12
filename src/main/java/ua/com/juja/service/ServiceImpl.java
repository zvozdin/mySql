package ua.com.juja.service;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;

import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {

    DatabaseManager manager;

    public ServiceImpl() {
        manager = new JDBCDatabaseManager();
    }

    @Override
    public List<String> commands() {
        return Arrays.asList("help", "menu", "connect");
    }

    @Override
    public void connect(String database, String user, String password) {
        manager.connect(database, user, password);
    }
}