package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InsertTest extends IntegrationTest {

    @Test
    public void testCreateWithoutConnect() {
        // given
        in.addCommand("insert|users|id|3|name|alex|password|0000");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // insert|users|id|3|name|alex|password|0000
                "You cannot use command 'insert|users|id|3|name|alex|password|0000' until you connect to database. " +
                "Use connect|database|user|password\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testCreateAfterConnect() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("clear|users");
        in.addCommand("insert|users|id|3|name|alex|password|0000");
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
                // clear|users
                "Table 'users' is cleared!\r\n" +
                "Enter a command or help\r\n" +
                // insert|users|id|3|name|alex|password|0000
                "Record 'columns:[id, name, password], values:[3, alex, 0000]' added.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("insert|users|errors");
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
                // insert|users|errors
                "Failed by a reason ==> Invalid number of parameters separated by '|'. " +
                "Expected even count. You enter ==> 3. " +
                "Use command 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testCreateToNonExistingTable() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("insert|non-existing_table|id|3|name|alex|password|0000");
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
                // insert|non-existing_table|id|3|name|alex|password|0000
                "Failed by a reason ==> Table 'non-existing_table' doesn't exist.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}