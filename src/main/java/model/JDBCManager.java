package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCManager {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {


        String database = "business";
        String user = "root";
        String password = "root";
        connection = connect(database, user, password);
        DatabaseMetaData data = connection.getMetaData();
        ResultSet tables = data.getTables(null, null, "%", null);
        List result = new ArrayList<String>();
        while (tables.next()) {
            result.add(tables.getString("TABLE_NAME"));
        }
        System.out.println(result.toString());

    }

    private static Connection connect(String database, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database + "?autoReconnect=true&useSSL=false", user, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format("Can't get connection for database: %s, user: %s ",
                    database, user), e);
        }
    }
}
