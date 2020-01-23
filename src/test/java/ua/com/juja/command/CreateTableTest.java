package ua.com.juja.command;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CreateTableTest {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateTable(manager, view);
    }

    @Test
    public void testCanProcess_CorrectCreateTableCommand() {
        assertTrue(command.canProcess("create|test|id"));
    }

    @Test
    public void testCanProcess_WrongCreateTableCommand() {
        assertFalse(command.canProcess("create"));
    }

    @Test
    public void testProcess_CreateTable() {
        // given
        DataSet input = new DataSet();
        input.put("id", "");

        // when
        command.process("create|test|id");

        // then
        verify(manager, atMostOnce()).createTable("test", input);
        verify(view).write("Table 'test' created.");
    }

    @Test
    public void testProcess_CreateTableCommandWithInvalidParametersNumber() {
        // when
        try {
            command.process("create|test|");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected no less than 3. You enter ==> 2.\n" +
                    "Use command 'create|tableName|columnName'", e.getMessage());
        }
    }
}