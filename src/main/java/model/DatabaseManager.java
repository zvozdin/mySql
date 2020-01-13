package model;

import java.util.List;

public interface DatabaseManager {

    void connect(String database, String user, String password);

    void createTable(String tableName, DataSet input);

    void dropTable(String tableName);

    List<String> getTablesNames(); // TODO make return  sorted table list

    List<String> getTableColumns(String tableName);

    List<DataSet> getTableData(String tableName);

    void clear(String tableName);

    void insert(String tableName, DataSet input);

    void update(String tableName, DataSet newValue, int id);

    boolean isConnected();
}