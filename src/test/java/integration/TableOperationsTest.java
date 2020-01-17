package integration;

import controller.Main;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TableOperationsTest extends IntegrationTest {

    @Test
    public void test_CreateTable_List_DropTable() {
        // given
        in.addCommand("list");
        in.addCommand("create|test|id|name|password");
        in.addCommand("list");
        in.addCommand("drop|test");
        in.addCommand("list");
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
                // list
                "[]\r\n" +
                "Enter a command or help\r\n" +
                // create|test|id|name|password
                "Table 'test' created.\r\n" +
                "========================\r\n" +
                "|id|name|password|\r\n" +
                "========================\r\n" +
                "Enter a command or help\r\n" +
                // list
                "[test]\r\n" +
                "Enter a command or help\r\n" +
                // drop|test
                "Table 'test' deleted.\r\n" +
                "Enter a command or help\r\n" +
                "[]\r\n" +
                "Enter a command or help\r\n" +
                // dropDatabase|testedDatabase
                "Database 'testedDatabase' deleted.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }

    @Test
    public void test_Insert_Insert_Update_Delete_ClearTable() {
        // given
        in.addCommand("create|test|id|name|password");
        in.addCommand("insert|tableName|id|1|name|user1|password|1111");
        in.addCommand("insert|tableName|id|2|name|user2|password|0000");
        in.addCommand("update|tableName|column1|value1|column2|value2");
        in.addCommand("drop|test");
        in.addCommand("list");
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
                // list
                "[]\r\n" +
                "Enter a command or help\r\n" +
                // create|test|id|name|password
                "Table 'test' created.\r\n" +
                "========================\r\n" +
                "|id|name|password|\r\n" +
                "========================\r\n" +
                "Enter a command or help\r\n" +
                // list
                "[test]\r\n" +
                "Enter a command or help\r\n" +
                // drop|test
                "Table 'test' deleted.\r\n" +
                "Enter a command or help\r\n" +
                "[]\r\n" +
                "Enter a command or help\r\n" +
                // dropDatabase|testedDatabase
                "Database 'testedDatabase' deleted.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}