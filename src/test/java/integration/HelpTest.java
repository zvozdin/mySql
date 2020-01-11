package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelpTest extends IntegrationTest {

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
                "\tclear|tableName\r\n" +
                "\t\tto delete content from the 'tableName'\r\n" +
                "\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\tto record content to the 'tableName'\r\n" +
                "\texit\r\n" +
                "\t\tto exit from the programm\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}