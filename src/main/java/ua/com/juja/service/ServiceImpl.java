package ua.com.juja.service;

import ua.com.juja.command.*;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;

import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public List<Command> commands() {
        return Arrays.asList(
                new Help(),
                new Connect(),
                new CreateDatabase(),
                new DropDatabase(),
                new Find(),
                new Clear()
        );
    }

    @Override
    public DatabaseManager connect(String database, String user, String password) {
        DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(database, user, password);
        return manager;
    }

    @Override
    public void newDatabase(DatabaseManager manager, String databaseName) {
        manager.createDatabase(databaseName);
    }

    @Override
    public void dropDatabase(DatabaseManager manager, String databaseName) {
        manager.dropDatabase(databaseName);
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) {
        return getTableData(manager, tableName);
    }

    @Override
    public void clear(DatabaseManager manager, String tableName) {
        manager.clear(tableName);
    }
}