package ua.com.juja.service;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;

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
    public List<List<String>> find(DatabaseManager manager, String tableName) {
        return getTableData(manager, tableName);
    }

    @Override
    public List<List<String>> clear(DatabaseManager manager, String table) {
        manager.clear(table);

        return find(manager, table);
    }
}