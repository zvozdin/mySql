package integration;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class IntegrationTest {

    protected static ConfigurableInput in;
    private static ByteArrayOutputStream out;

    // to test on your computer enter your MySql databaseName, user, password below
    // todo create enum class for parameters to connect to db
    public String databaseName = "business";
    private String user = "root";
    private String password = "root";

    public String testedDatabaseName = "testedDatabase";

    @Before
    public void setup() {
        in = new ConfigurableInput();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

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