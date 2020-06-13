package ua.com.juja.dao;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConnectParameters {

    public static String database = "";
    public static String user = "";
    public static String password = "";
    public static String driver = "";
    public static String url = "";
    public static String ssl = "";

    public static void get() {
        try (FileReader fileReader = new FileReader("src\\main\\resources\\db.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            database = properties.getProperty("databaseName");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driverMySQL");
            url = properties.getProperty("url");
            ssl = properties.getProperty("establishingSSL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}