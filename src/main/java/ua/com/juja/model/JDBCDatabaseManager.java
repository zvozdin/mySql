package ua.com.juja.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;

    @Override
    public void connect(String database, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database + "?autoReconnect=true&useSSL=false", user, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format(
                    "Can't get connection for database: %s, user: %s ",
                    database, user), e);
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("create database " + databaseName);
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format(
                    "Database '%s' already exists", databaseName), e);
        }
    }

    @Override
    public void dropDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop database " + databaseName);
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format(
                    "Database '%s' doesn't exist", databaseName), e);
        }
    }

    @Override
    public void createTable(String tableName, DataSet input) {
        if (getTablesNames().contains(tableName)){
            throw new IllegalArgumentException(String.format(
                    "Table '%s' already exists", tableName));
        }
        String sql = "create table " + tableName + " (";

        List<String> columns = input.getNames(); // TODO extract to separate method
        for (String name : columns) {
            sql += name + " VARCHAR(45) NOT NULL,";
        }
        sql += " PRIMARY KEY (`" + columns.get(0) + "`))";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable(String tableName) {
        notExistingTableValidation(tableName);
        try (Statement statement = connection.createStatement()) {
            statement.execute("drop table " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getTablesNames() {
        List<String> result = new LinkedList<>();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet tables = data.getTables(null, null, "%", null);
            while (tables.next()) {
                result.add(tables.getString("TABLE_NAME"));
            }
            tables.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        notExistingTableValidation(tableName);
        List<String> result = new LinkedList<>();
        DatabaseMetaData data = null;
        try {
            data = connection.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet columnsNames = data.getColumns(
                null, null, /*"%"*/tableName, null)) {
            while (columnsNames.next()) {
                result.add(columnsNames.getString(4));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        notExistingTableValidation(tableName);
        List<DataSet> result = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from " + tableName)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                DataSet dataSet = new DataSet();
                result.add(dataSet);
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    dataSet.put(metaData.getColumnName(i + 1), resultSet.getObject(i + 1));
                }
            }

            // return empty table (for example after clear table) with only columnNames and without values
            if (result.size() == 0) {
                List<String> columns = getTableColumns(tableName);
                DataSet dataSet = new DataSet();
                for (String name : columns) {
                    dataSet.put(name, "");
                }
                result.add(dataSet);
                return result;
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public void clear(String tableName) {
        notExistingTableValidation(tableName);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String tableName, DataSet input) {
        notExistingTableValidation(tableName);
        try (Statement statement = connection.createStatement()) {
            String columns = getColumnNamesFormated(input, "%s, ");
            String values = getValuesFormated(input, "'%s', ");
            statement.executeUpdate(
                    "insert into " + tableName + " (" + columns + ") values (" + values + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, DataSet set, DataSet where) {
        notExistingTableValidation(tableName);
        String columnsSet = getColumnNamesFormated(set, "%s = ?, ");
        String columnsWhere = getColumnNamesFormated(where, "%s = ?, ");

        String sql = "UPDATE " + tableName + " SET " + columnsSet + " WHERE " + columnsWhere;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            List<Object> valuesSet = set.getValues();
            for (Object elementData : valuesSet) {
                statement.setObject(index++, elementData);
            }

            List<Object> valuesWhere = where.getValues();
            for (Object elementData : valuesWhere) {
                statement.setObject(index, elementData);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRow(String tableName, DataSet deleteValue) {
        notExistingTableValidation(tableName);
        String columnNames = getColumnNamesFormated(deleteValue, "%s = ?, ");
        String sql = "delete from " + tableName + " where " + columnNames;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Object> values = deleteValue.getValues();
            int index = 1;
            for (Object elementData : values) {
                preparedStatement.setObject(index++, elementData);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public boolean isDatabaseExist(String databaseName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();
            while (resultSet.next()) {
                String data = resultSet.getString(1);
                if (databaseName.equals(data)) {
                    return true;
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void notExistingTableValidation(String tableName) {
        if (!getTablesNames().contains(tableName)) {
            throw new IllegalArgumentException(String.format("Table '%s' doesn't exist", tableName));
        }
    }

    private String getColumnNamesFormated(DataSet input, String format) {
        String result = "";
        List<String> columnNames = input.getNames();
        for (String column : columnNames) {
            result += String.format(format, column);
        }
        return result.substring(0, result.length() - 2);
    }

    private String getValuesFormated(DataSet input, String format) {
        String result = "";
        List<Object> values = input.getValues();
        for (Object value : values) {
            result += String.format(format, value);
        }
        return result.substring(0, result.length() - 2);
    }
}