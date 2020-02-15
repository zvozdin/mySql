package ua.com.juja.model;

import ua.com.juja.view.ActionMessages;

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
            throw new IllegalArgumentException(String.format(
                    ActionMessages.DATABASE_EXISTS.toString(), databaseName));
        }
        databases.add(databaseName);
    }

    @Override
    public void dropDatabase(String databaseName) {
        if (!isDatabaseExist(databaseName)) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.NOT_EXISTING_DATABASE.toString(), databaseName));
        }
        databases.remove(databaseName);
    }

    @Override
    public void createTable(String tableName, Set<String> columns) {
        if (tables.contains(tableName)) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.CREATE_EXISTING_TABLE.toString(), tableName));
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
    public List<String> getTables() {
        return tables;
    }

    @Override
    public Set<String> getColumns(String tableName) {
        notExistingTableValidation(tableName);
        return columns;
    }

    @Override
    public List<List<String>> getRows(String tableName) {
        notExistingTableValidation(tableName);

        List<List<String>> rows = new ArrayList<>();
        for (Map<String, String> element : data) {
            List<String> row = new ArrayList<>();
            for (Object value : element.values()) {
                row.add(value.toString());
            }
            rows.add(row);
        }
        return rows;
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
                    ActionMessages.NOT_EXISTING_TABLE, tableName));
        }
    }
}