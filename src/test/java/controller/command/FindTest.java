package controller.command;

import model.DataSet;
import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.View;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Find command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);
    }

    @Test
    public void testCanProcess_CorrectFindCommand() {
        assertTrue(command.canProcess("find|users"));
    }

    @Test
    public void testCanProcess_WrongFindCommand() {
        assertFalse(command.canProcess("find.users"));
    }

    @Test
    public void testProcess_FindData() {
        // given
        when(manager.getTablesNames()).thenReturn(
                Arrays.asList(new String[]{"products", "shops", "users"}));

        when(manager.getTableColumns("users")).thenReturn(
                Arrays.asList(new String[]{"id", "name", "password"}));

        List<DataSet> users = new LinkedList<>();
        DataSet user1 = new DataSet();
        user1.put("id", "11");
        user1.put("name", "Alex");
        user1.put("password", "****");

        DataSet user2 = new DataSet();
        user2.put("id", "12");
        user2.put("name", "Sasha");
        user2.put("password", "++++");

        users.add(user1);
        users.add(user2);
        when(manager.getTableData("users")).thenReturn(users);

        // when
        command.process("find|users");

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());

        assertEquals("[" +
                "========================, " +
                "|id|name|password|, " +
                "========================, " +
                "|11|Alex|****|, " +
                "|12|Sasha|++++|]", captor.getAllValues().toString());
    }

    @Test
    public void testProcess_FindNonExistingTable() {
        // given
        when(manager.getTablesNames()).thenReturn(
                Arrays.asList(new String[]{"products", "shops", "users"}));

        // when
        try {
            command.process("find|nonExisting");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Table 'nonExisting' doesn't exist.", e.getMessage());
        }
    }

    @Test
    public void testProcess_FindCommandWithWrongParameters() {
        // when
        try {
            command.process("find|users|wrongParameter");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid number of parameters separated by '|'. Expected 2. You enter ==> 3", e.getMessage());
        }
    }
}