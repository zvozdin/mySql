package ua.com.juja.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ua.com.juja.controller.ActionMessages;

import java.sql.*;
import java.util.*;

@Component
@Scope("prototype")
public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;
    private JdbcTemplate template;
    private String database;
    private String user;

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
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
            this.database = database;
            this.user = user;
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format(
                    ActionMessages.NO_CONNECTION.toString(),
                    database, user), e);
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        template.execute("create database " + databaseName);
    }

    @Override
    public List<String> getDatabases() {
        List<String> result = new LinkedList<>();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet databases = data.getCatalogs();
            while (databases.next()) {
                result.add(databases.getString(1));
            }

            databases.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public void dropDatabase(String databaseName) {
        template.update("drop database " + databaseName);
    }


    @Override
    public void createTable(String tableName, Set<String> columns) {
        existingTableValidation(tableName);

        String names = StringUtils
                .collectionToDelimitedString(columns, ",", "", " VARCHAR(45) NOT NULL");

        template.execute(String
                .format("create table %s ( %s, PRIMARY KEY (`%s`))",
                        tableName, names, columns.iterator().next()));
    }

    @Override
    public void dropTable(String tableName) {
        notExistingTableValidation(tableName);

        template.execute("drop table " + tableName);
    }

    @Override
    public List<String> getTables() {
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

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> getColumns(String tableName) {
        notExistingTableValidation(tableName);

        Set<String> set = new LinkedHashSet<>();

        return (Set<String>) template.query("select * from " + tableName,
                new ResultSetExtractor() {
                    @Override
                    public Set<String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        for (int i = 0; i < metaData.getColumnCount(); i++) {
                            String columnName = metaData.getColumnName(i + 1);
                            set.add(columnName);
                        }
                        return set;
                    }
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<List<String>> getRows(String tableName) {
        notExistingTableValidation(tableName);

        return template.query("select * from " + tableName,
                new RowMapper() {
                    @Override
                    public List<String> mapRow(ResultSet resultSet, int j) throws SQLException {
                        List<String> row = new ArrayList<>();
                        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                            row.add(resultSet.getString(i + 1));
                        }
                        return row;
                    }
                });
    }

    @Override
    public void clear(String tableName) {
        notExistingTableValidation(tableName);

        template.update("delete from " + tableName);
    }

    @Override
    public void insert(String tableName, Map<String, String> input) {
        notExistingTableValidation(tableName);

        String columns = String.join(",", input.keySet());
        String values = StringUtils
                .collectionToDelimitedString(input.values(), ",", "'", "'");

        template.update(String.format("insert into %s (%s) values (%s)",
                tableName, columns, values));
    }

    @Override
    public void update(String tableName, Map<String, String> set, Map<String, String> where) {
        notExistingTableValidation(tableName);

        String setColumns = StringUtils
                .collectionToDelimitedString(set.keySet(), ",", "", " = ?");
        String whereColumns = StringUtils
                .collectionToDelimitedString(where.keySet(), ",", "", " = ?");

        List<String> values = new LinkedList<>();
        values.addAll(set.values());
        values.addAll(where.values());

        template.update(String.format("update %s set %s where %s", tableName, setColumns, whereColumns),
                values.toArray());
    }

    @Override
    public void deleteRow(String tableName, Map<String, String> delete) {
        notExistingTableValidation(tableName);

        String columns = StringUtils
                .collectionToDelimitedString(delete.keySet(), ",", "", " = ?");

        List<String> values = new LinkedList<>();
        values.addAll(delete.values());

        template.update(String.format("delete from %s where %s", tableName, columns),
                values.toArray());
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public String getUserName() {
        return user;
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    private void existingDatabaseValidation(String databaseName) {
        if (getDatabases().contains(databaseName)) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.DATABASE_EXISTS.toString(), databaseName));
        }
    }

    private void notExistingDatabaseValidation(String databaseName) {
        if (!getDatabases().contains(databaseName)) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.NOT_EXISTING_DATABASE.toString(), databaseName));
        }
    }

    private void existingTableValidation(String tableName) {
        if (getTables().contains(tableName)) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.CREATE_EXISTING_TABLE.toString(), tableName));
        }
    }

    private void notExistingTableValidation(String tableName) {
        if (!getTables().contains(tableName)) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.NOT_EXISTING_TABLE.toString(), tableName));
        }
    }
}