package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClearTest extends IntegrationTest {

    @Test
    public void testClearWithoutConnect() {
        // given
        in.addCommand("clear|users");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // clear
                "You cannot use command 'clear|users' until you connect to database. " +
                "Use connect|database|user|password\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testClearAfterConnect() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("clear|users");
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
                // clear
                "Table 'users' is cleared!\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testClearNonExistingTable() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("clear|nonExistingTable");
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
                // clear|nonExistingTable
                "Failed by a reason ==> Table 'nonExistingTable' doesn't exist.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testClearWithError() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("clear|users|error");
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
                // clear|users|error
                "Failed by a reason ==> Invalid number of parameters separated by '|'. " +
                "Expected 2. You enter ==> 3\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}