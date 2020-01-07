package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCManagerTest {

    private JDBCManager manager;

    @Before
    public void setup() {
        manager = new JDBCManager();
        manager.connect("business", "root", "root");
    }

    @Test
    public void getTablesNames() {
        List tables = manager.getTablesNames();
        assertEquals("[products, shops, users]", tables.toString());
    }

    @Test
    public void getTableData() {
        List<DataSet> tableData = manager.getTableData("products");
        assertEquals("[" +
                "columnNames | [products_id, products_name, price, shop_id]\n" +
                "values | [1, Kolbasa, 150, 1]\n, " +
                "columnNames | [products_id, products_name, price, shop_id]\n" +
                "values | [2, Cheese, 100, 1]\n" +
                "]", tableData.toString());
    }
}