package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class DatabaseManagerTest {

    private DatabaseManager manager;
    private String testedDatabaseName;

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
        // create DB
        manager.createDatabase("createdDB");
        assertTrue(manager.isDatabaseExist("createdDB".toLowerCase()));

        // drop DB
        manager.dropDatabase("createdDB");
        assertFalse(manager.isDatabaseExist("createdDB".toLowerCase()));
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

        // drop
        manager.dropTable("test");
        assertEquals("[]", manager.getTablesNames().toString());
    }

    @Test
    public void test_CreateTable_GetTableColumns() {
        // when
        manager.createTable("test", getDataSetForTable());

        // then
        assertEquals("[id, name, password]",
                manager.getTableColumns("test").toString());
    }

    @Test
    public void test_Insert_GetTableData_ClearTable() {
        // given
        manager.createTable("test", getDataSetForTable());

        // when
        manager.insert("test", getDataSetForTable());

        // then table data
        assertEquals("[columns:[id, name, password], values:[1, Alex, 1111]]",
                manager.getTableData("test").toString());

        // when insert additional
        DataSet addValue = new DataSet();
        addValue.put("id", "5");
        addValue.put("name", "Olena");
        addValue.put("password", "7777");
        manager.insert("test", addValue);

        // then 2 rows
        assertEquals("[" +
                        "columns:[id, name, password], values:[1, Alex, 1111], " +
                        "columns:[id, name, password], values:[5, Olena, 7777]]",
                manager.getTableData("test").toString());

        // when
        manager.clear("test");

        // then clear table
        assertEquals("[columns:[id, name, password], values:[, , ]]",
                manager.getTableData("test").toString());
    }

    @Test
    public void test_UpdateTableData_DeleteRow() {
        // given
        manager.createTable("test", getDataSetForTable());
        manager.insert("test", getDataSetForTable());

        // when update
        DataSet newValue = new DataSet();
        newValue.put("name", "SashaChanged");
        newValue.put("password", "0000Changed");
        manager.update("test", newValue, 1);

        //then update
        assertEquals("[columns:[id, name, password], values:[1, SashaChanged, 0000Changed]]",
                manager.getTableData("test").toString());

        // when delete
        DataSet deleteValue = new DataSet();
        deleteValue.put("name", "SashaChanged");
        manager.deleteRow("test", deleteValue);

        // then delete row
        assertEquals("[columns:[id, name, password], values:[, , ]]",
                manager.getTableData("test").toString());
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }

    private DataSet getDataSetForTable() {
        DataSet input = new DataSet();
        input.put("id", "1");
        input.put("name", "Alex");
        input.put("password", "1111");
        return input;
    }
}