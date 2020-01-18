package model;

import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    private List<DataSet> data = new LinkedList<>();
    private List<String> tables = new LinkedList<>();
    private List<String> columns = new LinkedList<>();
    private boolean isDatabaseExists = false;

    @Override
    public void connect(String database, String user, String password) {
        // do nothing
    }

    @Override
    public void createDatabase(String databaseName) {
        isDatabaseExists = true;
    }

    @Override
    public void dropDatabase(String databaseName) {
        isDatabaseExists = false;
    }

    @Override
    public void createTable(String tableName, DataSet input) {
        if (tables.contains(tableName)) {
            throw new RuntimeException(String.format("Table '%s' already exists", tableName), new RuntimeException());
        }
        tables.add(tableName);
        columns = input.getNames();
    }

    @Override
    public void dropTable(String tableName) {
        if (!tables.contains(tableName)){
            throw new RuntimeException(String.format("Table '%s' doesn't exist", tableName), new RuntimeException());
        }
        tables.remove(tableName);
    }

    @Override
    public List<String> getTablesNames() {
        return tables;
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        return columns;
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        if (data.isEmpty()) {
            // if tableName is empty then return only columnNames without data
            clear(tableName);
        }
        return data;
    }

    @Override
    public void clear(String tableName) {
        data.clear();
        DataSet dataSet = new DataSet();
        for (String column : columns) {
            dataSet.put(column, "");
        }
        data.add(dataSet);
    }

    @Override
    public void insert(String tableName, DataSet input) {
        data.add(input);
    }

    @Override
    public void update(String tableName, DataSet set, DataSet where) {
        String column = where.getNames().get(0);
        Object value = where.getValues().get(0);
        for (DataSet element : data) {
            if (element.get(column).equals(value)) {
                element.update(set);
            }
        }
    }

    @Override
    public void deleteRow(String tableName, DataSet delete) {
        String column = delete.getNames().get(0);
        Object value = delete.getValues().get(0);
        for (DataSet element : data) {
            if (element.get(column).equals(value)) {
                data.remove(element);
            }
        }
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public boolean isDatabaseExist(String databaseName) {
        return isDatabaseExists;
    }

    @Override
    public void closeConnection() {
        // do nothing
    }
}