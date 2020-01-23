package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.view.View;

import static org.junit.Assert.*;

public class UnsupportedTest {

    private View view = Mockito.mock(View.class);
    private Command command;

    @Before
    public void setup() {
        command = new Unsupported(view);
    }

    @Test
    public void testCanProcess_Command() {
        assertTrue(command.canProcess(""));
    }

    @Test
    public void testCanProcess_NotExistingCommand() {
        assertTrue(command.canProcess("notExisting"));
    }

    @Test
    public void testExitProcess() {
        // when
        command.process("notExisting");

        // then
        Mockito.verify(view).write("Not existing 'notExisting' command.");
    }
}