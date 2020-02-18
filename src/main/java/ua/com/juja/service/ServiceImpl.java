package ua.com.juja.service;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;
import ua.com.juja.view.ActionMessages;

import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public List<String> commands() {
        return Arrays.asList(
                "help",
                "menu",
                "connect",
                "find",
                "newDatabase",
                "dropDatabase",
                "create table",
                "clear"
        );
    }

    @Override
    public DatabaseManager connect(String database, String user, String password) {
        DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(database, user, password);

        return manager;
    }

    @Override
    public String newDatabase(DatabaseManager manager, String databaseName) {
        manager.createDatabase(databaseName);
        return String.format(ActionMessages.DATABASE_NEW.toString(), databaseName);
    }

    @Override
    public String dropDatabase(DatabaseManager manager, String databaseName) {
        manager.dropDatabase(databaseName);
        return String.format(ActionMessages.DROP_DB.toString(), databaseName);
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) {
        return getTableData(manager, tableName);
    }

    @Override
    public List<List<String>> clear(DatabaseManager manager, String tableName) {
        manager.clear(tableName);
        return find(manager, tableName);
    }
}