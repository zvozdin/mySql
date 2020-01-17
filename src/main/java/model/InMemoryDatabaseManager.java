package model;

import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    private List<DataSet> data = new LinkedList<>();
    private List<String> tables = new LinkedList<>();
    private List<String> columns = new LinkedList<>();
    private boolean isDatabaseExist = false;

    @Override
    public void connect(String database, String user, String password) {
        // do nothing
    }

//    @Override
//    public void disconnect() {
//        // do nothing
//    }

    @Override
    public void createDatabase(String databaseName) {
        isDatabaseExist = true;
    }

    @Override
    public void dropDatabase(String databaseName) {
        isDatabaseExist = false;
    }

    @Override
    public void createTable(String tableName, DataSet input) {
        tables.add(tableName);
        columns = input.getNames();
    }

    @Override
    public void dropTable(String tableName) {
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
        if (data.isEmpty()){
            // if tableName is empty then return only columnNames without data
            clear(tableName);
        }
        return data;
    }

    @Override
    public void clear(String tableName) {
        data.clear();
        DataSet dataSet = new DataSet();
        for (String name : columns) {
            dataSet.put(name, "");
        }
        data.add(dataSet);
    }

    @Override
    public void insert(String tableName, DataSet input) {
        data.add(input);
    }

    @Override
    public void update(String tableName, DataSet newValue, int id) {
        for (DataSet element : data) {
            if (element.get("id").toString().equals(String.valueOf(id))) {
                element.update(newValue);
            }
        }
    }

    @Override
    public void deleteRow(String tableName, DataSet deleteValue) {
        // todo refactor this
        List<Object> values = deleteValue.getValues();
        for (DataSet element : data) {
            List<Object> elementValues = element.getValues();
            for (Object val : values) {
                for (Object elementVal : elementValues) {
                    if (val.toString().equals(elementVal.toString())) {
                        data.remove(element);
                    }
                }
            }
        }
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public boolean isDatabaseExist(String databaseName) {
        return isDatabaseExist;
    }

    @Override
    public void closeConnection() {
        // do nothing
    }
}