package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup() {
//        manager = new JDBCDatabaseManager();
        manager = new InMemoryDatabaseManager();
        manager.connect("business", "root", "root");
    }

    @Test
    public void testGetTablesNames() {
        List tables = manager.getTablesNames();
        assertEquals("[products, shops, users]", tables.toString());
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
        assertEquals("[" +
                "[id, name, password]\n" +
                "[1, Alex, 1111]]", tableData.toString());
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
        assertEquals("[" +
                "[id, name, password]\n" +
                "[1, Sasha, 0000]]", tableData.toString());
    }
}