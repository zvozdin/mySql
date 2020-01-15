package controller.command;

import model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectTest {

    private DatabaseManager manager;
    private View view;
    private Connect command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(manager, view);
    }

    @Test
    public void testCanProcess_CorrectConnectCommand() {
        assertTrue(command.canProcess("connect|"));
    }

    @Test
    public void testCanProcess_WrongConnectCommand() {
        assertFalse(command.canProcess("connect.business|root|root"));
    }

    @Test
    public void testProcess_Connect() {
        //when
        command.process("connect|business|root|root");

        //then
        verify(manager).connect("business","root", "root");
        verify(view).write("Success!");
    }

    @Test
    public void testProcess_ConnectCommandWithWrongParameters() {
        //when
        try {
            command.process("connect|business");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("" +
                    "Invalid number of parameters separated by '|'. Expected 4. You enter ==> 2", e.getMessage());
        }
    }
}