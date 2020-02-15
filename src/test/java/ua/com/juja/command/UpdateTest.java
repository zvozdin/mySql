package ua.com.juja.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.*;

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
        List<Map<String, String>> users = new ArrayList<>();

        Map<String, String> user1 = new LinkedHashMap<>();
        user1.put("id", "1");
        user1.put("name", "user1");
        user1.put("password", "1111");
        users.add(user1);

        Map<String, String> set = new LinkedHashMap<>();
        set.put("password", "0000");
        user1.putAll(set);

        List<List<String>> rows = new ArrayList<>();
        for (Map<String, String> element : users) {
            List<String> row = new ArrayList<>();
            for (String value : element.values()) {
                row.add(value);
            }
            rows.add(row);
        }

        when(manager.getColumns("users"))
                .thenReturn(new LinkedHashSet<>(new LinkedList<>(Arrays.asList("id", "name", "password"))));
        when(manager.getRows("users"))
                .thenReturn(rows);

        // when
        command.process("update|users|password|0000|name|user1");

        // then
        verify(manager, atMostOnce()).update("users", set, user1);
        verify(manager).getColumns("users");
        verify(manager).getRows("users");

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