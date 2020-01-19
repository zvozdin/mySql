package controller.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DropTableTest {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DropTable(manager, view);
    }

    @Test
    public void testCanProcess_CorrectDropTableCommand() {
        assertTrue(command.canProcess("drop|tableName"));
    }

    @Test
    public void testCanProcess_WrongDropTableCommand() {
        assertFalse(command.canProcess("drop"));
    }

    @Test
    public void testProcess_DropTable() {
        // when
        command.process("drop|test");

        // then
        verify(manager).dropTable("test");
        verify(view).write("Table 'test' deleted.");
    }

    @Test
    public void testProcess_DropTableCommandWithInvalidParametersNumber() {
        // when
        try {
            command.process("drop|");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 2. You enter ==> 1.\n" +
                    "Use command 'drop|tableName'", e.getMessage());
        }
    }
}