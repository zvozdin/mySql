package ua.com.juja.command;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.view.View;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeleteRowTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DeleteRow(manager, view);
    }

    @Test
    public void testCanProcess_CorrectDeleteRowCommand() {
        assertTrue(command.canProcess("delete|tableName|column|value"));
    }

    @Test
    public void testCanProcess_WrongDeleteRowCommand() {
        assertFalse(command.canProcess("delete"));
    }

    @Test
    public void testProcess_DeleteRow() {
        // given
        List<DataSet> users = new LinkedList<>();
        DataSet user1 = new DataSet();
        user1.put("id", "1");
        user1.put("name", "user1");
        user1.put("password", "1111");
        users.add(user1);

        DataSet user2 = new DataSet();
        user2.put("id", "2");
        user2.put("name", "user2");
        user2.put("password", "0000");
        users.add(user2);

        when(manager.getTableColumns("users")).thenReturn(
                Arrays.asList(new String[]{"id", "name", "password"}));
        when(manager.getTableData("users")).thenReturn(users);

        // when
        users.remove(user2);
        command.process("delete|users|name|user2");

        // then
        verify(manager).getTableColumns("users");
        verify(manager).getTableData("users");
        verify(manager, atMostOnce()).deleteRow("delete|users|name|user2", user2);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());

        assertEquals("[" +
                "Record 'user2' deleted., " +
                "+------+----------+------------+\n" +
                "|  id  |   name   |  password  |\n" +
                "+------+----------+------------+\n" +
                "|  1   |  user1   |    1111    |\n" +
                "+------+----------+------------+]", captor.getAllValues().toString());
    }

    @Test
    public void testProcess_DeleteRowCommandWithInvalidParametersNumber() {
        // when
        try {
            command.process("delete|users|user2");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 4. You enter ==> 3.\n" +
                    "Use command 'delete|tableName|column|value'", e.getMessage());
        }
    }
}