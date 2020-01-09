package model;

import java.util.List;

public interface DatabaseManager {

    void connect(String database, String user, String password);

    List<String> getTablesNames();

    List<String> getTableColumns(String tableName);

    List<DataSet> getTableData(String tableName);

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, DataSet newValue, int id);
}
