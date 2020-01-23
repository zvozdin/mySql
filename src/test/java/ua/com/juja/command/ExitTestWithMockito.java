package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.view.View;

import static org.junit.Assert.*;

public class ExitTestWithMockito {

    private View view = Mockito.mock(View.class);
    private DatabaseManager manager = Mockito.mock(DatabaseManager.class);
    private Command exit;

    @Before
    public void setup() {
        exit = new Exit(manager, view);
    }

    @Test
    public void testCanProcess_ExitCommand() {
        assertTrue(exit.canProcess("exit"));
    }

    @Test
    public void testCanProcess_NotExitCommand() {
        assertFalse(exit.canProcess("notExit"));
    }

    @Test
    public void testExitProcess() {
        // when
        try {
            exit.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }

        // then
        Mockito.verify(view).write("See you soon!");
    }
}