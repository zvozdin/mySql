package ua.com.juja.model;

import ua.com.juja.view.TableGenerator;

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
    public void createTable(String tableName, List<String> columns) {
        if (tables.contains(tableName)) {
            throw new IllegalArgumentException(String.format("" +
                    "Table '%s' already exists", tableName), new IllegalArgumentException());
        }
        tables.add(tableName);
        this.columns = columns;
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
    public String getDataInTableFormat(String tableName) {
        notExistingTableValidation(tableName);

        List<List<String>> rowsList = new ArrayList<>();
        for (DataSet row : data) {
            List<String> stringRow = new ArrayList<>();
            for (Object value : row.getValues()) {
                stringRow.add(value.toString());
            }
            rowsList.add(stringRow);
        }
        return new TableGenerator().generateTable(columns, rowsList);
    }

    @Override
    public void clear(String tableName) {
        notExistingTableValidation(tableName);
        data = new ArrayList<>();
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