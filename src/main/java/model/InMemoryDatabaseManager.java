package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryDatabaseManager implements DatabaseManager {

    List<DataSet> data = new ArrayList<>();
    private String table1 = "products";
    private String table2 = "shops";
    private String table3 = "users";

    @Override
    public void connect(String database, String user, String password) {
        //do nothing
    }

    @Override
    public List getTablesNames() {
        return Arrays.asList(new String[]{table1, table2, table3});
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        return data;
    }

    @Override
    public void clear(String tableName) {
        data = new ArrayList<>();
    }

    @Override
    public void create(String tableName, DataSet input) {
        data.add(input);
    }

    @Override
    public void update(String tableName, DataSet newValue, int id) {
        for (DataSet element : data) {
            if (element.get("id").toString().equals(String.valueOf(id))) {
                element.update(newValue);
            }
        }
    }
}
