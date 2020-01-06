package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCManagerTest {

    JDBCManager manager = new JDBCManager();

    @Before
    public void setup() {
        manager.connect("business", "root", "root");
    }

    @Test
    public void getTablesNames() {
        List tables = manager.getTablesNames();
        assertEquals("[products, shops, users]", tables.toString());
    }
}