package integration;

import controller.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExitTest extends IntegrationTest {

    @Test
    public void testExit() {
        // given
        in.addCommand("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("" +
                "Hello, User!\r\n" +
                "Enter the Database name, Username and Password in the format: " +
                "'connect|database|user|password' or help\r\n" +
                // exit
                "See you soon!\r\n", getOutput());
    }
}