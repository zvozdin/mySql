package ua.com.juja.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InsertTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Insert(manager, view);
    }

    @Test
    public void testCanProcess_CorrectInsertCommand() {
        assertTrue(command.canProcess("insert|"));
    }

    @Test
    public void testCanProcess_WrongInsertCommand() {
        assertFalse(command.canProcess("insert"));
    }

    @Test
    public void testProcess_InsertData() {
        // given
        DataSet user1 = new DataSet();
        user1.put("id", "1");
        user1.put("name", "user1");
        user1.put("password", "1111");

        List<DataSet> users = new ArrayList<>();

        // when
        users.add(user1);
        command.process("insert|users|id|1|name|user1|password|1111");

        // then
        verify(manager, atMostOnce()).insert("users", user1);
        verify(view).write("Record '[1, user1, 1111]' added.");
    }

    @Test
    public void testProcess_InsertCommandWithInvalidParametersNumber() {
        // not even parameters count
        try { // TODO separate into 2 tests
            command.process("insert|tableName|column|value|invalid");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected even count. You enter ==> 5.\n" +
                    "Use command 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'", e.getMessage());
        }

        // min parameters count
        try {
            command.process("insert|tableName|");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected min 4. You enter ==> 2.\n" +
                    "Use command 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'", e.getMessage());
        }
    }
}