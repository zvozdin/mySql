package controller.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateDatabaseTest {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateDatabase(manager, view);
    }

    @Test
    public void testCanProcess_CorrectCreateDatabaseCommand() {
        assertTrue(command.canProcess("newDatabase|databaseName"));
    }

    @Test
    public void testCanProcess_WrongCreateDatabaseCommand() {
        assertFalse(command.canProcess("newDatabase"));
    }

    @Test
    public void testProcess_CreateDatabase() {
        // when
        command.process("newDatabase|databaseName");

        // then
        verify(manager).createDatabase("databaseName");
        verify(view).write("Database 'databaseName' created.");
    }

    @Test
    public void testProcess_CreateDatabaseCommandWithWrongParameters() {
        // when
        try {
            command.process("newDatabase|");
            fail("Expected InvalidParametersNumberException");
        } catch (Exception e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 2. You enter ==> 1.\n" +
                    "Use command 'newDatabase|databaseName'", e.getMessage());
        }
    }
}