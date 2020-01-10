package integration;

import controller.Main;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static ConfigurableInput in;
    private static ByteArrayOutputStream out;

    @Before
    public void setup() {
        in = new ConfigurableInput();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit() {
        // given
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testHelp() {
        // given
        in.addCommand("help");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // help
                "Existing commands:\r\n" +
                "\thelp\r\n" +
                "\t\tto display a list of commands\r\n" +
                "\tconnect|database|user|password\r\n" +
                "\t\tto connect to database\r\n" +
                "\tlist\r\n" +
                "\t\tto display a list of tables\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tto retrieve content from the 'tableName'\r\n" +
                "\texit\r\n" +
                "\t\tto exit from the programm\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testUnsupported() {
        // given
        in.addCommand("unsupported");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // unsupported
                "You cannot use command 'unsupported' until you connect to database. " +
                "Use connect|database|user|password\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("unsupported");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // connect
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // unsupported
                "Non Existent command ==> unsupported\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testListWithoutConnect() {
        // given
        in.addCommand("list");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // list
                "You cannot use command 'list' until you connect to database. " +
                "Use connect|database|user|password\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testListAfterConnect() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("list");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // connect
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // list
                "[products, shops, users]\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("find|users");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // connect
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // find|users
                "========================\r\n" +
                "|id|name|password|\r\n" +
                "========================\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testConnectAfterConnect() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("list");
        in.addCommand("connect|test|root|root");
        in.addCommand("list");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // connect to business db
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // list
                "[products, shops, users]\r\n" +
                "Enter a command or help\r\n" +
                // connect to test db
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // list
                "[test_table]\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    private String getOutput() {
        try {
            return new String(out.toByteArray(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
