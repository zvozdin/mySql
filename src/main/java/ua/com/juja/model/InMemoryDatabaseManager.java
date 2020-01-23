package ua.com.juja.model;

import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    private List<DataSet> data = new LinkedList<>();
    private List<String> tables = new LinkedList<>();
    private List<String> databases = new LinkedList<>();
    private List<String> columns = new LinkedList<>();

    @Override
    public void connect(String database, String user, String password) {
        // do nothing
    }

    @Override
    public void createDatabase(String databaseName) {
        if (isDatabaseExist(databaseName)) {
            throw new RuntimeException(String.format(
                    "Database '%s' already exists", databaseName), new RuntimeException());
        }
        databases.add(databaseName);
    }

    @Override
    public void dropDatabase(String databaseName) {
        if (!isDatabaseExist(databaseName)) {
            throw new RuntimeException(String.format(
                    "Database '%s' doesn't exist", databaseName), new RuntimeException());
        }
        databases.remove(databaseName);
    }

    @Override
    public void createTable(String tableName, DataSet input) {
        if (tables.contains(tableName)) {
            throw new IllegalArgumentException(String.format("" +
                    "Table '%s' already exists", tableName), new IllegalArgumentException());
        }
        tables.add(tableName);
        columns = input.getNames();
    }

    @Override
    public void dropTable(String tableName) {
        notExistingTableValidation(tableName);
        tables.remove(tableName);
    }

    @Override
    public List<String> getTablesNames() {
        return tables;
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        notExistingTableValidation(tableName);
        return columns;
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        notExistingTableValidation(tableName);
        if (data.isEmpty()) {
            // if tableName is empty then return only columnNames without data
            clear(tableName);
        }
        return data;
    }

    @Override
    public void clear(String tableName) {
        notExistingTableValidation(tableName);
        data.clear();
        DataSet dataSet = new DataSet();
        for (String column : columns) {
            dataSet.put(column, "");
        }
        data.add(dataSet);
    }

    @Override
    public void insert(String tableName, DataSet input) {
        notExistingTableValidation(tableName);
        data.add(input);
    }

    @Override
    public void update(String tableName, DataSet set, DataSet where) {
        notExistingTableValidation(tableName);
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
        notExistingTableValidation(tableName);
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
        return databases.contains(databaseName);
    }

    @Override
    public void closeConnection() {
        // do nothing
    }

    private void notExistingTableValidation(String tableName) {
        if (!tables.contains(tableName)) {
            throw new IllegalArgumentException(String.format("" +
                    "Table '%s' doesn't exist", tableName), new IllegalArgumentException());
        }
    }
}