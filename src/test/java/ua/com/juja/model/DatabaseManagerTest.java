package ua.com.juja.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public abstract class DatabaseManagerTest {

    private DatabaseManager manager;
    private String testedDatabaseName = "testedDatabase";

    public abstract DatabaseManager getDatabaseManager();

    @Before
    public void setUp() {
        manager = getDatabaseManager();

        ConnectParameters.get();
        String database = ConnectParameters.database;
        String user = ConnectParameters.user;
        String password = ConnectParameters.password;
        manager.connect(database, user, password);

        testedDatabaseName = "testedDatabase";
        manager.createDatabase(testedDatabaseName);
        manager.connect(testedDatabaseName, user, password);
    }

    @After
    public void tearDown() {
        manager.dropDatabase(testedDatabaseName);
    }

    @Test
    public void test_CreateDatabase_DropDatabase() {
        // create database
        manager.createDatabase("_test");
        assertTrue(manager.isDatabaseExist("_test"));

        // create already existing database
        try {
            manager.createDatabase("_test");
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Database '_test' already exists", e.getMessage());
        }

        // drop database
        manager.dropDatabase("_test");
        assertFalse(manager.isDatabaseExist("_test"));

        // drop non existing database
        try {
            manager.dropDatabase("_test");
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Database '_test' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_GetTablesNames_EmptyDatabase() {
        assertEquals("[]", manager.getTablesNames().toString());
    }

    @Test
    public void test_CreateTable_DropTable() {
        // create
        manager.createTable("test", getDataForTable().keySet());
        assertEquals("[test]", manager.getTablesNames().toString());

        // create already existing table
        try {
            manager.createTable("test", getDataForTable().keySet());
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Table 'test' already exists", e.getMessage());
        }

        // drop
        manager.dropTable("test");
        assertEquals("[]", manager.getTablesNames().toString());

        // drop non existing table
        try {
            manager.dropTable("test");
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Table 'test' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_GetTableColumns() {
        // when
        manager.createTable("test", getDataForTable().keySet());

        // then
        assertEquals("[id, name, password]",
                manager.getTableColumns("test").toString());
    }

    @Test
    public void test_GetTableColumnsFromNotExistingTable() {
        // when
        try {
            manager.getTableColumns("nonExistingTable");
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_GetTableFormatDataNonExistingTable() {
        // when
        try {
            manager.getTableFormatData("nonExistingTable");
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_Insert_GetTableFormatData() {
        // given
        manager.createTable("test", getDataForTable().keySet());

        // when
        manager.insert("test", getDataForTable());

        // then
        assertEquals("" +
                "+------+----------+------------+\n" +
                "|  id  |   name   |  password  |\n" +
                "+------+----------+------------+\n" +
                "|  1   |  user1   |    1111    |\n" +
                "+------+----------+------------+", manager.getTableFormatData("test"));
    }

    @Test
    public void test_InsertTwoRecords() {
        // given
        manager.createTable("test", getDataForTable().keySet());
        manager.insert("test", getDataForTable());

        // when
        Map<String, String> second = new LinkedHashMap<>();
        second.put("id", "5");
        second.put("name", "user2");
        second.put("password", "7777");

        manager.insert("test", second);

        // then
        assertEquals("" +
                "+------+----------+------------+\n" +
                "|  id  |   name   |  password  |\n" +
                "+------+----------+------------+\n" +
                "|  1   |  user1   |    1111    |\n" +
                "|  5   |  user2   |    7777    |\n" +
                "+------+----------+------------+", manager.getTableFormatData("test"));
    }

    @Test
    public void test_InsertIntoNonExistingTable() {
        // when
        try {
            manager.insert("nonExistingTable", getDataForTable());
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_Clear() {
        // given
        manager.createTable("test", getDataForTable().keySet());
        manager.insert("test", getDataForTable());

        // when
        manager.clear("test");

        // then
        assertEquals("" +
                "+------+--------+------------+\n" +
                "|  id  |  name  |  password  |\n" +
                "+------+--------+------------+\n" +
                "+------+--------+------------+", manager.getTableFormatData("test"));
    }


    @Test
    public void test_ClearNonExistingTable() {
        // when
        try {
            manager.clear("nonExistingTable");
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_UpdateTableData() {
        // given
        manager.createTable("test", getDataForTable().keySet());
        manager.insert("test", getDataForTable());

        // when
        Map<String, String> set = new LinkedHashMap<>();
        set.put("name", "user1Changed");
        set.put("password", "0000Changed");

        Map<String, String> where = new LinkedHashMap<>();
        where.put("id", "1");

        manager.update("test", set, where);

        //then
        assertEquals("" +
                "+------+----------------+----------------+\n" +
                "|  id  |      name      |    password    |\n" +
                "+------+----------------+----------------+\n" +
                "|  1   |  user1Changed  |  0000Changed   |\n" +
                "+------+----------------+----------------+", manager.getTableFormatData("test"));
    }

    @Test
    public void test_UpdateNotExistingTableData() {
        // when
        Map<String, String> set = new LinkedHashMap<>();
        set.put("name", "user1Changed");
        set.put("password", "0000Changed");

        Map<String, String> where = new LinkedHashMap<>();
        where.put("id", "1");

        try {
            manager.update("nonExistingTable", set, where);
            fail("Expected Exception");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_DeleteRow() {
        // given
        manager.createTable("test", getDataForTable().keySet());
        manager.insert("test", getDataForTable());

        // when
        Map<String, String> delete = new LinkedHashMap<>();
        delete.put("name", "user1");

        manager.deleteRow("test", delete);

        // then
        assertEquals("" +
                "+------+--------+------------+\n" +
                "|  id  |  name  |  password  |\n" +
                "+------+--------+------------+\n" +
                "+------+--------+------------+", manager.getTableFormatData("test"));
    }

    @Test
    public void test_DeleteRowNotExistingTable() {
        // when
        Map<String, String> delete = new LinkedHashMap<>();
        delete.put("name", "user1");

        try {
            manager.deleteRow("nonExistingTable", delete);
            fail("Expected Exception");
        } catch (Exception e) {
            // then
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }

    private Map<String, String> getDataForTable() {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("id", "1");
        input.put("name", "user1");
        input.put("password", "1111");
        return input;
    }
}