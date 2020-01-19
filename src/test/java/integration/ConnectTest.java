package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnectTest extends IntegrationTest {

    @Test
    public void testConnectAfterConnect() {
        // given
        in.addCommand("dropDatabase|" + testedDatabaseName);
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // connect|yourDatabase|user|password
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // newDatabase|testedDatabase
                "Database 'testedDatabase' created.\r\n" +
                "Enter a command or help\r\n" +
                // connect|testedDatabase|user|password
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // dropDatabase|testedDatabase
                "Database 'testedDatabase' deleted.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testConnectWithError() {
        // given
        in.addCommand("connect|" + databaseName);
        in.addCommand("dropDatabase|" + testedDatabaseName);
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // connect|yourDatabase|user|password
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // newDatabase|testedDatabase
                "Database 'testedDatabase' created.\r\n" +
                "Enter a command or help\r\n" +
                // connect|testedDatabase|user|password
                "Success!\r\n" +
                "Enter a command or help\r\n" +
                // connect|databaseName
                "Failed by a reason ==> Invalid parameters number separated by '|'.\n" +
                "Expected 4. You enter ==> 2.\n" +
                "Use command 'connect|business|root|root'\r\n" +
                "Enter a command or help\r\n" +
                // dropDatabase|testedDatabase
                "Database 'testedDatabase' deleted.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}