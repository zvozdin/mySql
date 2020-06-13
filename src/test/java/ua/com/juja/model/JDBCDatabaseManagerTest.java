package ua.com.juja.model;

import ua.com.juja.dao.DatabaseManager;
import ua.com.juja.dao.JDBCDatabaseManager;

public class JDBCDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }
}