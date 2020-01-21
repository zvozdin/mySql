package controller.command;

import model.DataSet;
import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UpdateTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Update(manager, view);
    }

    @Test
    public void testCanProcess_CorrectUpdateCommand() {
        assertTrue(command.canProcess("update|tableName|column1|value1|column2|value2"));
    }

    @Test
    public void testCanProcess_WrongUpdateCommand() {
        assertFalse(command.canProcess("update"));
    }

    @Test
    public void testProcess_UpdateData() {
        // given
        List<DataSet> users = new ArrayList<>();

        DataSet user1 = new DataSet();
        user1.put("id", "1");
        user1.put("name", "user1");
        user1.put("password", "1111");
        users.add(user1);

        DataSet set = new DataSet();
        set.put("password", "0000");

        when(manager.getTableData("users")).thenReturn(users);
        when(manager.getTableColumns("users")).thenReturn(
                Arrays.asList(new String[]{"id", "name", "password"}));

        // when
        user1.update(set);
        command.process("update|users|password|0000|name|user1");

        // then
        verify(manager, atMostOnce()).update("users", set, user1);
        verify(manager).getTableColumns("users");
        verify(manager).getTableData("users");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());

        assertEquals("[" +
                "Record 'user1' updated., " +
                "+------+----------+------------+\n" +
                "|  id  |   name   |  password  |\n" +
                "+------+----------+------------+\n" +
                "|  1   |  user1   |    0000    |\n" +
                "+------+----------+------------+]", captor.getAllValues().toString());
    }

    @Test
    public void testProcess_UpdateCommandWithInvalidParametersNumber() {
        // when
        try {
            command.process("update|tableName|column1|value1|column2|");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 6. You enter ==> 5.\n" +
                    "Use command 'update|tableName|column1|value1|column2|value2'", e.getMessage());
        }
    }
}