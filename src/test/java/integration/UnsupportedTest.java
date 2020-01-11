package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnsupportedTest extends IntegrationTest {

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
}