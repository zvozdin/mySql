package model;

import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    private String table1 = "products";
    private String table2 = "shops";
    private String table3 = "users";

    List<DataSet> data = new LinkedList<>();
    private List<String> tables = new LinkedList<>(); // TODO create Set not same TablesNames
    private List<String> columns; // TODO make by Map columnsNames appropriated tableName


    @Override
    public void connect(String database, String user, String password) {
        tables.add(table1);
        tables.add(table2);
        tables.add(table3);
    }

    @Override
    public void createTable(String tableName, DataSet input) {
        tables.add(0, tableName); // TODO make check on CreateTable Command for same TableName
        columns = input.getNames();
    }

    @Override
    public void dropTable(String tableName) {
        for (String table : tables) {
            if (tableName.equals(table)) {
                tables.remove(tableName);
            }
        }
    }

    @Override
    public List<String> getTablesNames() {
        return tables;
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        return Arrays.asList(new String[]{"id", "name", "password"});
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
    public void insert(String tableName, DataSet input) {
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

    @Override
    public boolean isConnected() {
        return true;
    }
}
