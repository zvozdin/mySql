package controller.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IsConnectedTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new IsConnected(manager, view);
    }

    @Test
    public void testCanProcess_EmptyCommandWithNullConnection() {
        // given
        when(manager.isConnected()).thenReturn(false);

        //then
        verify(manager, atMostOnce()).isConnected();
        assertTrue(command.canProcess(""));
    }

    @Test
    public void testCanProcess_AnyCommandWithConnection() {
        // given
        when(manager.isConnected()).thenReturn(true);

        //then
        verify(manager, atMostOnce()).isConnected();
        assertFalse(command.canProcess("any"));
    }

    @Test
    public void testProcess_WithFalseConnection() {
        // when
        command.process("list");

        // then
        verify(view).write("" +
                "You cannot use command 'list' until you connect to database. Use connect|database|user|password");
    }
}