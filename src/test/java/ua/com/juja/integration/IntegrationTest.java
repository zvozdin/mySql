package ua.com.juja.integration;

import org.junit.Before;
import ua.com.juja.ConnectParameters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class IntegrationTest {

    protected static ConfigurableInput in;
    private static ByteArrayOutputStream out;

    public String testedDatabaseName = "testedDatabase";

    @Before
    public void setup() {
        in = new ConfigurableInput();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

        ConnectParameters.get();
        String databaseName = ConnectParameters.database;
        String user = ConnectParameters.user;
        String password = ConnectParameters.password;

        in.addCommand("connect|" + databaseName + "|" + user + "|" + password);
        // create and connect to newDatabase for testing
        in.addCommand("newDatabase|" + testedDatabaseName);
        in.addCommand("connect|" + testedDatabaseName + "|" + user + "|" + password);
    }

    public String getOutput() {
        try {
            return new String(out.toByteArray(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}