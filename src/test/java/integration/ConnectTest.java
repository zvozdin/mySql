package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnectTest extends IntegrationTest {

    @Test
    public void testConnect() {
        // given
        in.addCommand("connect|business|root|root");
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
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testConnectWithError() {
        // given
        in.addCommand("connect|business");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // connect with wrong parameters
                "Failed by a reason ==> Invalid number of parameters separated by '|'. " +
                "Expected 4. You enter ==> 2\r\n" +
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
                "[]\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}