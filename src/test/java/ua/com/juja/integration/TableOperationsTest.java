package ua.com.juja.integration;

import ua.com.juja.Main;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TableOperationsTest extends IntegrationTest {

    @Test
    public void test_CreateTable_List_Find_DropTable() {
        // given
        in.addCommand("list");
        in.addCommand("create|test|id|name|password");
        in.addCommand("list");
        in.addCommand("find|test");
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
                "Enter a command or help\r\n" +
                // list
                "[test]\r\n" +
                "Enter a command or help\r\n" +
                // find|test
                "+------+--------+------------+\n" +
                "|  id  |  name  |  password  |\n" +
                "+------+--------+------------+\n" +
                "+------+--------+------------+\r\n" +
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
        in.addCommand("insert|test|id|1|name|user1|password|1111");
        in.addCommand("insert|test|id|2|name|user2|password|0000");
        in.addCommand("update|test|password|7777|name|user1");
        in.addCommand("delete|test|name|user2");
        in.addCommand("clear|test");
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
                // create|test|id|name|password
                "Table 'test' created.\r\n" +
                "Enter a command or help\r\n" +
                // insert|test|id|1|name|user1|password|1111
                "Record '[1, user1, 1111]' added.\r\n" +
                "Enter a command or help\r\n" +
                // insert|test|id|2|name|user2|password|0000
                "Record '[2, user2, 0000]' added.\r\n" +
                "Enter a command or help\r\n" +
                // update|test|password|7777|name|user1
                "Record 'user1' updated.\r\n" +
                "+------+----------+------------+\n" +
                "|  id  |   name   |  password  |\n" +
                "+------+----------+------------+\n" +
                "|  1   |  user1   |    7777    |\n" +
                "|  2   |  user2   |    0000    |\n" +
                "+------+----------+------------+\r\n" +
                "Enter a command or help\r\n" +
                // delete|test|name|user2
                "Record 'user2' deleted.\r\n" +
                "+------+----------+------------+\n" +
                "|  id  |   name   |  password  |\n" +
                "+------+----------+------------+\n" +
                "|  1   |  user1   |    7777    |\n" +
                "+------+----------+------------+\r\n" +
                "Enter a command or help\r\n" +
                // clear|test
                "Table 'test' is cleared!\r\n" +
                "Enter a command or help\r\n" +
                // dropDatabase|testedDatabase
                "Database 'testedDatabase' deleted.\r\n" +
                "Enter a command or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}