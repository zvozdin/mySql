package model;

import static org.junit.Assert.*;

public class InMemoryDatabaseManagerTest extends DatabaseManagerTest{

    @Override
    public DatabaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }
}