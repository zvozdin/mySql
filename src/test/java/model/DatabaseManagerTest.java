package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DatabaseManagerTest {

    private DatabaseManager manager;

    public abstract DatabaseManager getDatabaseManager();

    @Before
    public void setUp() {
        manager = getDatabaseManager();

        // to test on your computer enter your MySql databaseName, user, password below
        String database = "business";
        String user = "root";
        String password = "root";
        manager.connect(database, user, password);
        // todo try to create database for each method and drop at the end method or @after
    }

    @Test
    public void testGetTablesNames() {
        assertEquals("[products, shops, users]", manager.getTablesNames().toString());
    }

    @Test
    public void testCreateTable() {
        // given
        DataSet input = new DataSet();
        input.put("id", "1");
        input.put("name", "alex");
        input.put("password", "1111");

        // when
        manager.createTable("test", input);

        // then
        assertEquals("[products, shops, test, users]", manager.getTablesNames().toString());

        manager.dropTable("test");
    }

    @Test
    public void testDropTable() {
        // given
        DataSet input = new DataSet();
        input.put("id", "");
        manager.createTable("created_table", input);

        // when
        manager.dropTable("created_table");

        // then
        assertEquals("[products, shops, users]", manager.getTablesNames().toString());
    }

    @Test
    public void testGetTableColumns() {
        assertEquals("[id, name, password]", manager.getTableColumns("users").toString());
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("id", "1");
        input.put("name", "Alex");
        input.put("password", "1111");

        // when
        manager.insert("users", input);

        // then
        assertEquals("" +
                "[columns:[id, name, password], values:[1, Alex, 1111]]",
                manager.getTableData("users").toString());
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("id", "1");
        input.put("name", "Alex");
        input.put("password", "1111");
        manager.insert("users", input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("name", "Sasha");
        newValue.put("password", "0000");
        manager.update("users", newValue, 1);

        //then
        assertEquals("" +
                "[columns:[id, name, password], values:[1, Sasha, 0000]]",
                manager.getTableData("users").toString());
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }
}