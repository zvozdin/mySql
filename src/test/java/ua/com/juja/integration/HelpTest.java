package ua.com.juja.integration;

import ua.com.juja.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelpTest extends IntegrationTest {

    @Test
    public void testHelp() {
        // given
        in.addCommand("help");
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
                "Success!\r\n"  +
                "Enter a command or help\r\n" +
                // help
                "Existing commands:\r\n" +
                "\thelp\r\n" +
                "\t\tto display a list of commands\r\n" +
                "\tconnect|databaseName|user|password\r\n" +
                "\t\tto connect to the database\r\n" +
                "\tnewDatabase|databaseName\r\n" +
                "\t\tto create a new database\r\n" +
                "\tdropDatabase|databaseName\r\n" +
                "\t\tto delete the database\r\n" +
                "\tlist\r\n" +
                "\t\tto display a list of tables\r\n" +
                "\tcreate|tableName|column1|column2|...|columnN\r\n" +
                "\t\tto create a new table\r\n" +
                "\tdrop|tableName\r\n" +
                "\t\tto delete the table\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tto retrieve content from the 'tableName'\r\n" +
                "\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\tto record content to the 'tableName'\r\n" +
                "\tupdate|tableName|column1|value1|column2|value2\r\n" +
                "\t\tto update the content in the 'tableName'\r\n" +
                "\t\t\tset column1 = value1 where column2 = value2\r\n" +
                "\tdelete|tableName|column|value\r\n" +
                "\t\tto delete content where column = value\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tto delete content from the 'tableName'\r\n" +
                "\texit\r\n" +
                "\t\tto exit from the program\r\n" +
                "Enter a command or help\r\n" +
                // dropDatabase|testedDatabase
                "Database 'testedDatabase' deleted.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}