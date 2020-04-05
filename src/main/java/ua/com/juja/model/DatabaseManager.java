package ua.com.juja.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

    void connect(String database, String user, String password);

    List<String> getDatabases();

    void createDatabase(String databaseName);

    void dropDatabase(String databaseName);

    void createTable(String tableName, Set<String> input);

    void dropTable(String tableName);

    List<String> getTables();

    Set<String> getColumns(String tableName);

    List<List<String>> getRows(String tableName);

    void clear(String tableName);

    void insert(String tableName, Map<String, String> input);

    void update(String tableName, Map<String, String> set, Map<String, String> where);

    void deleteRow(String string, Map<String, String> delete);

    boolean isConnected();
}