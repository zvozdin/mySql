package model;

import java.sql.*;
import java.util.*;

public class JDBCManager {

    private Connection connection;

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

    public List getTablesNames() {
        List result = new LinkedList();
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

    public List<DataSet> getTableData(String tableName) {
        List<DataSet> result = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from " + tableName))
        {
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

    public void clear(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(String tableName, DataSet input) {
        try (Statement statement = connection.createStatement()) {
            String nameFormated = getNameFormated(input);
            String valuesFormated = getValuesFormated(input);
            statement.executeUpdate("insert into " + tableName + " (" + nameFormated + ") values (" +
                    valuesFormated +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getNameFormated(DataSet input) {
        String result = "";
        List<String> columnNames = input.getNames();
        for (String column : columnNames) {
            result += column + ", ";
        }
        return result.substring(0, result.length() - 2);
    }

    private String getValuesFormated(DataSet input) {
        String result = "'";
        List<Object> values = input.getValues();
        for (Object elementData : values) {
            result += elementData.toString() + "', '";
        }
        return result.substring(0, result.length() - 3);
    }
}
