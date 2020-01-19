package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class DatabaseManagerTest {

    private DatabaseManager manager;
    private String testedDatabaseName = "testedDatabase";

    public abstract DatabaseManager getDatabaseManager();

    @Before
    public void setUp() {
        manager = getDatabaseManager();

        // to test on your computer enter your MySql databaseName, user, password below
        String database = "business";
        String user = "root";
        String password = "root";
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
        } catch (Exception e) {
            assertEquals("Database '_test' already exists", e.getMessage());
        }

        // drop database
        manager.dropDatabase("_test");
        assertFalse(manager.isDatabaseExist("_test"));

        // drop non existing database
        try {
            manager.dropDatabase("_test");
            fail("Expected Exception");
        } catch (Exception e) {
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
        manager.createTable("test", getDataSetForTable());
        assertEquals("[test]", manager.getTablesNames().toString());

        // create already existing table
        try {
            manager.createTable("test", getDataSetForTable());
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'test' already exists", e.getMessage());
        }

        // drop
        manager.dropTable("test");
        assertEquals("[]", manager.getTablesNames().toString());

        // drop non existing table
        try {
            manager.dropTable("test");
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'test' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_CreateTable_GetTableColumns() {
        // when
        manager.createTable("test", getDataSetForTable());

        // then
        assertEquals("[id, name, password]",
                manager.getTableColumns("test").toString());

        // getTableColumns from non existing table
        try {
            manager.getTableColumns("nonExistingTable");
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_Insert_GetTableData_ClearTable() {
        // given
        manager.createTable("test", getDataSetForTable());

        // when
        manager.insert("test", getDataSetForTable());

        // then table data
        assertEquals("[columns:[id, name, password], values:[1, user1, 1111]]",
                manager.getTableData("test").toString());

        // when insert additional
        DataSet addValue = new DataSet();
        addValue.put("id", "5");
        addValue.put("name", "user2");
        addValue.put("password", "7777");
        manager.insert("test", addValue);

        // then 2 rows
        assertEquals("[" +
                        "columns:[id, name, password], values:[1, user1, 1111], " +
                        "columns:[id, name, password], values:[5, user2, 7777]]",
                manager.getTableData("test").toString());

        // when
        manager.clear("test");

        // then clear table
        assertEquals("[columns:[id, name, password], values:[, , ]]",
                manager.getTableData("test").toString());

        // getTableData from non existing table
        try {
            manager.getTableData("nonExistingTable");
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }

        // clear non existing table
        try {
            manager.clear("nonExistingTable");
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_UpdateTableData_DeleteRow() {
        // given
        manager.createTable("test", getDataSetForTable());
        manager.insert("test", getDataSetForTable());

        // insert into non existing table
        try {
            manager.insert("nonExistingTable", getDataSetForTable());
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }

        // when update
        DataSet set = new DataSet();
        set.put("name", "user1Changed");
        set.put("password", "0000Changed");

        DataSet where = new DataSet();
        where.put("id", "1");
        manager.update("test", set, where);

        //then update
        assertEquals("[columns:[id, name, password], values:[1, user1Changed, 0000Changed]]",
                manager.getTableData("test").toString());

        // update into non existing table
        try {
            manager.update("nonExistingTable", set, where);
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }

        // when delete
        DataSet delete = new DataSet();
        delete.put("name", "user1Changed");
        manager.deleteRow("test", delete);

        // then delete row
        assertEquals("[columns:[id, name, password], values:[, , ]]",
                manager.getTableData("test").toString());

        // delete into non existing table
        try {
            manager.deleteRow("nonExistingTable", delete);
            fail("Expected Exception");
        } catch (Exception e) {
            assertEquals("Table 'nonExistingTable' doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }

    private DataSet getDataSetForTable() {
        DataSet input = new DataSet();
        input.put("id", "1");
        input.put("name", "user1");
        input.put("password", "1111");
        return input;
    }
}