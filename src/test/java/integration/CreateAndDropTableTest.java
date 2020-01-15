package integration;

import controller.Main;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CreateAndDropTableTest extends IntegrationTest {

    @Test
    public void test_CreateTable_DropTable() {
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
}