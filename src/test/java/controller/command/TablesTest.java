package controller.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import view.View;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TablesTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Tables(manager, view);
    }

    @Test
    public void testCanProcess_CorrectListCommand() {
        assertTrue(command.canProcess("list"));
    }

    @Test
    public void testCanProcess_WrongListCommand() {
        assertFalse(command.canProcess("list|"));
    }

    @Test
    public void testProcess_List() {
        // given
        when(manager.getTablesNames()).thenReturn(Arrays.asList(new String[]{"test1", "test2"}));

        // when
        command.process("list");

        // then
        verify(manager).getTablesNames();
        verify(view).write("[test1, test2]");
    }

    @Test
    public void testProcess_EmptyList() {
        // given
        when(manager.getTablesNames()).thenReturn(Arrays.asList(new String[0]));

        // when
        command.process("list");

        // then
        verify(manager).getTablesNames();
        verify(view).write("[]");
    }
}