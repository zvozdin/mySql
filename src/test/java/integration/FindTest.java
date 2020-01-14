package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FindTest extends IntegrationTest {

    @Test
    public void testFindWithoutConnect() {
        // given
        in.addCommand("find|users");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // find|users
                "You cannot use command 'find|users' until you connect to database. " +
                "Use connect|database|user|password\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("clear|users");
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
                // clear
                "Table 'users' is cleared!\r\n" +
                "Enter a command or help\r\n" +
                // find|users
                "========================\r\n" +
                "|id|name|password|\r\n" +
                "========================\r\n" +
                "||||\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testFindAfterConnectWithData() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("clear|users");
        in.addCommand("insert|users|id|3|name|alex|password|0000");
        in.addCommand("insert|users|id|5|name|sasha|password|1111");
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
                // clear
                "Table 'users' is cleared!\r\n" +
                "Enter a command or help\r\n" +
                // insert|users|id|3|name|alex|password|0000
                "Record 'columns:[id, name, password], values:[3, alex, 0000]' added.\r\n" +
                "Enter a command or help\r\n" +
                // insert|users|id|5|name|sasha|password|1111
                "Record 'columns:[id, name, password], values:[5, sasha, 1111]' added.\r\n" +
                "Enter a command or help\r\n" +
                "========================\r\n" +
                "|id|name|password|\r\n" +
                "========================\r\n" +
                "|3|alex|0000|\r\n" +
                "|5|sasha|1111|\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testFindNonExistingTable() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("find|nonExistingTable");
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
                // find|nonExistingTable
                "Failed by a reason ==> Table 'nonExistingTable' doesn't exist.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}