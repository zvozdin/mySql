package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateTest extends IntegrationTest {

    @Test
    public void testCreateWithoutConnect() {
        // given
        in.addCommand("create|users|id|3|name|alex|password|0000");
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // create|users|id|3|name|alex|password|0000
                "You cannot use command 'create|users|id|3|name|alex|password|0000' until you connect to database. " +
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
        in.addCommand("create|users|id|3|name|alex|password|0000");
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
                // create|users|id|3|name|alex|password|0000
                "Record 'columns:[id, name, password], values:[3, alex, 0000]' added.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("create|users|errors");
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
                // create|users|errors
                "Failed by a reason ==> Invalid number of parameters separated by '|'. " +
                "Expected even count. You enter ==> 3. " +
                "Use command 'create|tableName|column1|value1|column2|value2|...|columnN|valueN'\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void testCreateToNonExistingTable() {
        // given
        in.addCommand("connect|business|root|root");
        in.addCommand("create|non-existing_table|id|3|name|alex|password|0000");
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
                // create|non-existing_table|id|3|name|alex|password|0000
                "Failed by a reason ==> Table 'non-existing_table' doesn't exist.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}