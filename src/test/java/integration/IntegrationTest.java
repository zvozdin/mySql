package integration;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class IntegrationTest {

    protected static ConfigurableInput in;
    private static ByteArrayOutputStream out;

    @Before
    public void setup() {
        in = new ConfigurableInput();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    public String getOutput() {
        try {
            return new String(out.toByteArray(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}