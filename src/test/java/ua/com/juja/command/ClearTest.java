package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        // when
        command.process("clear|test");

        // then
        verify(manager).clear("test");
        verify(view).write("Table 'test' is cleared!");
    }

    @Test
    public void testProcess_ClearCommandWithWithInvalidParametersNumber_1() {
        // when
        try {
            command.process("clear|");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 2. You enter ==> 1.\n" +
                    "Use command 'clear|tableName'", e.getMessage());
        }
    }

    @Test
    public void testProcess_ClearCommandWithWithInvalidParametersNumber_3() {
        // when
        try {
            command.process("clear|test|wrongParameter");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 2. You enter ==> 3.\n" +
                    "Use command 'clear|tableName'", e.getMessage());
        }
    }
}