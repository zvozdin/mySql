package controller.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import view.View;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void testCanProcess_CorrectFindCommand() {
        assertTrue(command.canProcess("clear|"));
    }

    @Test
    public void testCanProcess_WrongFindCommand() {
        assertFalse(command.canProcess("clear.users"));
    }

    @Test
    public void testProcess_ClearTable() {
        // given
        when(manager.getTablesNames())
                .thenReturn(Arrays.asList(new String[]{"test"}));

        // when
        command.process("clear|test");

        // then
        verify(manager).clear("test");
        verify(view).write("Table 'test' is cleared!");
    }

    @Test
    public void testProcess_ClearNonExistingTable() {
        // given
        when(manager.getTablesNames())
                .thenReturn(Arrays.asList(new String[]{"test"}));

        // when
        try {
            command.process("clear|nonExistingTable");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Table 'nonExistingTable' doesn't exist.", e.getMessage());
        }
    }

    @Test
    public void testProcess_ClearCommandWithWrongParameters() {
        // when
        try {
            command.process("clear|test|wrongParameter");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid number of parameters separated by '|'. Expected 2. You enter ==> 3", e.getMessage());
        }
    }
}