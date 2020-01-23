package ua.com.juja.controller.command;

import ua.com.juja.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DropDatabaseTest {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DropDatabase(manager, view);
    }

    @Test
    public void testCanProcess_CorrectDropDatabaseCommand() {
        assertTrue(command.canProcess("dropDatabase|databaseName"));
    }

    @Test
    public void testCanProcess_WrongDropDatabaseCommand() {
        assertFalse(command.canProcess("dropDatabase"));
    }

    @Test
    public void testProcess_DropDatabase() {
        // when
        command.process("dropDatabase|databaseName");

        // then
        verify(manager).dropDatabase("databaseName");
        verify(view).write("Database 'databaseName' deleted.");
    }

    @Test
    public void testProcess_DropDatabaseCommandWithWithInvalidParametersNumber() {
        // when
        try {
            command.process("dropDatabase|");
            fail("Expected InvalidParametersNumberException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 2. You enter ==> 1.\n" +
                    "Use command 'dropDatabase|databaseName'", e.getMessage());
        }
    }
}