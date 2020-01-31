package ua.com.juja.model;

import ua.com.juja.view.TableGenerator;

import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    private List<Map<String, String>> data = new LinkedList<>();
    private List<String> tables = new LinkedList<>();
    private List<String> databases = new LinkedList<>();
    private Set<String> columns = new LinkedHashSet<>();

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
    public void createTable(String tableName, Set<String> columns) {
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
    public Set<String> getTableColumns(String tableName) {
        notExistingTableValidation(tableName);
        return columns;
    }

    @Override
    public String getTableFormatData(String tableName) {
        notExistingTableValidation(tableName);

        List<List<String>> rows = new ArrayList<>();
        for (Map<String, String> element : data) {
            List<String> row = new ArrayList<>();
            for (Object value : element.values()) {
                row.add(value.toString());
            }
            rows.add(row);
        }
        return new TableGenerator().generateTable(new LinkedList<>(columns), rows); // TODO change 1st parametr in TAbleGenetrator to Set
    }

    @Override
    public void clear(String tableName) {
        notExistingTableValidation(tableName);
        data = new ArrayList<>();
    }

    @Override
    public void insert(String tableName, Map<String, String> input) {
        notExistingTableValidation(tableName);
        data.add(input);
    }

    @Override
    public void update(String tableName, Map<String, String> set, Map<String, String> where) {
        notExistingTableValidation(tableName);

        String column = where.keySet().iterator().next();
        String value = where.values().iterator().next();
        for (Map<String, String> element : data)
            if (element.get(column).equals(value)) {
                element.putAll(set);
            }
    }

    @Override
    public void deleteRow(String tableName, Map<String, String> delete) {
        notExistingTableValidation(tableName);
        String column = delete.keySet().iterator().next();
        Object value = delete.values().iterator().next();
        for (Map<String, String> element : data) {
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