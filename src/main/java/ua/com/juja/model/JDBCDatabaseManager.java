package ua.com.juja.model;

import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.TableGenerator;

import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;

    @Override
    public void connect(String database, String user, String password) {
        ConnectParameters.get();
        try {
            Class.forName(ConnectParameters.driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(ActionMessages.NO_DRIVER.toString(), e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    ConnectParameters.url + database + ConnectParameters.ssl, user, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format(
                    ActionMessages.NO_CONNECTION.toString(),
                    database, user), e);
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("create database " + databaseName);
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.DATABASE_EXISTS.toString(), databaseName), e);
        }
    }

    @Override
    public void dropDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop database " + databaseName);
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.NOT_EXISTING_DATABASE.toString(), databaseName), e);
        }
    }

    @Override
    public void createTable(String tableName, Set<String> columns) {
        if (getTablesNames().contains(tableName)) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.CREATE_EXISTING_TABLE.toString(), tableName));
        }
        String sql = "create table " + tableName + " (";

        for (String column : columns) {
            sql += column + " VARCHAR(45) NOT NULL,";
        }
        sql += " PRIMARY KEY (`" + columns.iterator().next() + "`))";

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
    public Set<String> getTableColumns(String tableName) {
        notExistingTableValidation(tableName);

        Set<String> result = new LinkedHashSet<>();
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
    public String getTableFormatData(String tableName) {
        notExistingTableValidation(tableName);

        Set<String> columns = getTableColumns(tableName);
        List<List<String>> rows = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from " + tableName)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    row.add(resultSet.getString(i + 1));
                }
                rows.add(row);
            }
            return new TableGenerator().generateTable(columns, rows);
        } catch (SQLException e) {
            return e.getMessage();
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
    public void insert(String tableName, Map<String, String> input) {
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
    public void update(String tableName, Map<String, String> set, Map<String, String> where) {
        notExistingTableValidation(tableName);

        String setColumns = getColumnNamesFormated(set, "%s = ?, ");
        String whereColumns = getColumnNamesFormated(where, "%s = ?, ");
        String sql = "UPDATE " + tableName + " SET " + setColumns + " WHERE " + whereColumns;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            Collection<String> setValues = set.values();
            for (String value : setValues) {
                statement.setString(index++, value);
            }

            Collection<String> whereValues = where.values();
            for (String value : whereValues) {
                statement.setString(index, value);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRow(String tableName, Map<String, String> delete) {
        notExistingTableValidation(tableName);

        String columns = getColumnNamesFormated(delete, "%s = ?, ");
        String sql = "delete from " + tableName + " where " + columns;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Collection<String> values = delete.values();
            int index = 1;
            for (String value : values) {
                preparedStatement.setString(index++, value);
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
            throw new IllegalArgumentException(String.format(
                    ActionMessages.NOT_EXISTING_TABLE.toString(), tableName));
        }
    }

    private String getColumnNamesFormated(Map<String, String> input, String format) {
        String result = "";
        Set<String> columns = input.keySet();
        for (String column : columns) {
            result += String.format(format, column);
        }
        return result.substring(0, result.length() - 2);
    }

    private String getValuesFormated(Map<String, String> input, String format) {
        String result = "";
        Collection<String> values = input.values();
        for (String value : values) {
            result += String.format(format, value);
        }
        return result.substring(0, result.length() - 2);
    }
}