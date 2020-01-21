package integration;

import org.junit.Before;
import testSettingsToConnectDB.ParametersToConnect;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class IntegrationTest {

    protected static ConfigurableInput in;
    private static ByteArrayOutputStream out;

    public String databaseName = ParametersToConnect.DATABASE;
    private String user = ParametersToConnect.USER;
    private String password = ParametersToConnect.PASSWORD;

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