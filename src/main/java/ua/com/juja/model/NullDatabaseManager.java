package ua.com.juja.model;

import java.util.*;

public class NullDatabaseManager implements DatabaseManager {

    @Override
    public void connect(String database, String user, String password) {

    }

    @Override
    public List<String> getDatabases() {
        return null;
    }

    @Override
    public void createDatabase(String databaseName) {

    }

    @Override
    public void dropDatabase(String databaseName) {

    }

    @Override
    public void createTable(String tableName, Set<String> input) {

    }

    @Override
    public void dropTable(String tableName) {

    }

    @Override
    public List<String> getTables() {
        return new LinkedList<>();
    }

    @Override
    public Set<String> getColumns(String tableName) {
        return new LinkedHashSet<>();
    }

    @Override
    public List<List<String>> getRows(String tableName) {
        return new LinkedList<>();
    }

    @Override
    public void clear(String tableName) {

    }

    @Override
    public void insert(String tableName, Map<String, String> input) {

    }

    @Override
    public void update(String tableName, Map<String, String> set, Map<String, String> where) {

    }

    @Override
    public void deleteRow(String string, Map<String, String> delete) {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isDatabaseExist(String databaseName) {
        return false;
    }

    @Override
    public void closeConnection() {

    }
}
