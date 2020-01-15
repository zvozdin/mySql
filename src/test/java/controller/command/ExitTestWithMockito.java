package controller.command;

import model.DatabaseManager;
import org.junit.Test;
import org.mockito.Mockito;
import view.View;

import static org.junit.Assert.*;

public class ExitTestWithMockito {

    private View view = Mockito.mock(View.class);
    private DatabaseManager manager = Mockito.mock(DatabaseManager.class);

    @Test
    public void testCanProcess_ExitCommand() {
        // given
        Command exit = new Exit(manager, view);

        // when
        boolean canProcess = exit.canProcess("exit");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcess_NotExitCommand() {
        // given
        Command exit = new Exit(manager, view);

        // when
        boolean canProcess = exit.canProcess("notExit");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testExitProcess() {
        // given
        Command exit = new Exit(manager, view);

        // when
        try {
            exit.process("exit");
            Mockito.verify(manager).disconnect();
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }

        // then
        Mockito.verify(view).write("See you soon!");
    }
}