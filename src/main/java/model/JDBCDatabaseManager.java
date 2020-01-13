package model;

import java.sql.*;
import java.util.*;

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
            throw new RuntimeException(String.format("Can't get connection for database: %s, user: %s ",
                    database, user), e);
        }
    }

    @Override
    public void createTable(String tableName, DataSet input) {
        String sql = "create table " + tableName + " (";

        List<String> columns = input.getNames();
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
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void clear(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String tableName, DataSet input) {
        try (Statement statement = connection.createStatement()) {
            String columnNames = getColumnNamesFormated(input, "%s, ");
            String valuesFormated = getValuesFormated(input);
            statement.executeUpdate("insert into " + tableName + " (" + columnNames + ") values (" +
                    valuesFormated +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, DataSet newValue, int id) {
        String columnNames = getColumnNamesFormated(newValue, "%s = ?, ");
        String sql = "update " + tableName + " set " + columnNames + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            List<Object> values = newValue.getValues();
            for (Object elementData : values) {
                statement.setObject(index++, elementData);
            }
            statement.setInt(index, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    private String getColumnNamesFormated(DataSet input, String format) {
        String result = "";
        List<String> columnNames = input.getNames();
        for (String column : columnNames) {
            result += String.format(format, column);
        }
        return result.substring(0, result.length() - 2);
    }

    private String getValuesFormated(DataSet input) {
        String result = "'";
        List<Object> values = input.getValues();
        for (Object elementData : values) {
            result += elementData + "', '";
        }
        return result.substring(0, result.length() - 3);
    }
}