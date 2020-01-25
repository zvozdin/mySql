package ua.com.juja;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConnectParameters {

    public static String database = "";
    public static String user = "";
    public static String password = "";

    public static void get() {
        try (FileReader fileReader = new FileReader("src\\main\\resources\\db_connect.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            database = properties.getProperty("databaseName");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}