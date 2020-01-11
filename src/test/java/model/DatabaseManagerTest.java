package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DatabaseManagerTest {

    private DatabaseManager manager;

    public abstract DatabaseManager getDatabaseManager();

    @Before
    public void setup() {
        manager = getDatabaseManager();
        manager.connect("business", "root", "root");
    }

    @Test
    public void testGetTablesNames() {
        List tables = manager.getTablesNames();
        assertEquals("[products, shops, users]", tables.toString());
    }

    @Test
    public void testGetTableColumns() {
        List columns = manager.getTableColumns("users");
        assertEquals("[id, name, password]", columns.toString());
    }

    @Test
    public void testGetTableData() {
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("id", "1");
        input.put("name", "Alex");
        input.put("password", "1111");
        manager.create("users", input);

        List<DataSet> tableData = manager.getTableData("users");
        assertEquals("[columns:[id, name, password], values:[1, Alex, 1111]]", tableData.toString());
    }

    @Test
    public void testUpdateTableData() {
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("id", "1");
        input.put("name", "Alex");
        input.put("password", "1111");
        manager.create("users", input);

        DataSet newValue = new DataSet();
        newValue.put("name", "Sasha");
        newValue.put("password", "0000");
        manager.update("users", newValue, 1);

        List<DataSet> tableData = manager.getTableData("users");
        assertEquals("[columns:[id, name, password], values:[1, Sasha, 0000]]", tableData.toString());
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }
}