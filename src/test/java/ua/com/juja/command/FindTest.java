package ua.com.juja.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

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
        List<Map<String, String>> users = new LinkedList<>();
        Map<String, String> user1 = new LinkedHashMap<>();
        user1.put("id", "11");
        user1.put("name", "user1");
        user1.put("password", "****");
        users.add(user1);

        Map<String, String> user2 = new LinkedHashMap<>();
        user2.put("id", "12");
        user2.put("name", "user2");
        user2.put("password", "++++");
        users.add(user2);

        when(manager.getTableFormatData("users"))
                .thenReturn("" +
                        "+------+----------+------------+\n" +
                        "|  id  |   name   |  password  |\n" +
                        "+------+----------+------------+\n" +
                        "|  11  |  user1   |    ****    |\n" +
                        "|  12  |  user2   |    ++++    |\n" +
                        "+------+----------+------------+");

        // when
        command.process("find|users");

        // then
        verify(manager).getTableFormatData("users");
        verify(view)
                .write("" +
                        "+------+----------+------------+\n" +
                        "|  id  |   name   |  password  |\n" +
                        "+------+----------+------------+\n" +
                        "|  11  |  user1   |    ****    |\n" +
                        "|  12  |  user2   |    ++++    |\n" +
                        "+------+----------+------------+");
    }

    @Test
    public void testProcess_FindEmptyTable() {
        // given
        when(manager.getTableFormatData("users"))
                .thenReturn("" +
                        "+------+--------+------------+\n" +
                        "|  id  |  name  |  password  |\n" +
                        "+------+--------+------------+\n" +
                        "+------+--------+------------");

        // when
        command.process("find|users");

        // then
        verify(manager).getTableFormatData("users");
        verify(view)
                .write("" +
                        "+------+--------+------------+\n" +
                        "|  id  |  name  |  password  |\n" +
                        "+------+--------+------------+\n" +
                        "+------+--------+------------");
    }

    @Test
    public void testProcess_FindCommandWithInvalidParametersNumber() {
        // when
        try {
            command.process("find|users|wrongParameter");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected 2. You enter ==> 3.\n" +
                    "Use command 'find|tableName'", e.getMessage());
        }
    }
}