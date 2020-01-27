package ua.com.juja.model;

import java.util.List;

public interface DatabaseManager {

    void connect(String database, String user, String password);

//    void disconnect();

    void createDatabase(String databaseName);

    void dropDatabase(String databaseName);

    void createTable(String tableName, List<String> input);

    void dropTable(String tableName);

    List<String> getTablesNames();

    List<String> getTableColumns(String tableName);

    List<DataSet> getTableData(String tableName);

    void clear(String tableName);

    void insert(String tableName, DataSet input);

    void update(String tableName, DataSet newValue, DataSet whereValue);

    void deleteRow(String string, DataSet deleteValue);

    boolean isConnected();

    boolean isDatabaseExist(String databaseName);

    void closeConnection();
}