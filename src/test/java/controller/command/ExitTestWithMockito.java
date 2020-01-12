package controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import view.View;

import static org.junit.Assert.*;

public class ExitTestWithMockito {

    private View view = Mockito.mock(View.class);

    @Test
    public void testExitCanProcess_ExitCommand() {
        // given
        Command exit = new Exit(view);

        // when
        boolean canProcess = exit.canProcess("exit");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testExitCanProcess_NotExitCommand() {
        // given
        Command exit = new Exit(view);

        // when
        boolean canProcess = exit.canProcess("notExit");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testExitProcess() {
        // given
        Command exit = new Exit(view);

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