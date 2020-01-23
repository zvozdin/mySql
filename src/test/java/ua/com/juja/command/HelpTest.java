package ua.com.juja.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HelpTest {

    private Command command;
    private View view;

    @Before
    public void setup() {
        view = mock(View.class);
        command = new Help(view);
    }

    @Test
    public void testCanProcess_HelpCommand() {
        assertTrue(command.canProcess("help"));
    }

    @Test
    public void testCanProcess_WrongHelpCommand() {
        assertFalse(command.canProcess("help|"));
    }

    @Test
    public void testProcess_Help() {
        // when
        command.process("help");

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());

        assertEquals("[Existing commands:\r\n" +
                "\thelp\r\n" +
                "\t\tto display a list of commands\r\n" +
                "\tconnect|databaseName|user|password\r\n" +
                "\t\tto connect to the database\r\n" +
                "\tnewDatabase|databaseName\r\n" +
                "\t\tto create a new database\r\n" +
                "\tdropDatabase|databaseName\r\n" +
                "\t\tto delete the database\r\n" +
                "\tlist\r\n" +
                "\t\tto display a list of tables\r\n" +
                "\tcreate|tableName|column1|column2|...|columnN\r\n" +
                "\t\tto create a new table\r\n" +
                "\tdrop|tableName\r\n" +
                "\t\tto delete the table\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tto retrieve content from the 'tableName'\r\n" +
                "\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\tto record content to the 'tableName'\r\n" +
                "\tupdate|tableName|column1|value1|column2|value2\r\n" +
                "\t\tto update the content in the 'tableName'\r\n" +
                "\t\t\tset column1 = value1 where column2 = value2\r\n" +
                "\tdelete|tableName|column|value\r\n" +
                "\t\tto delete content where column = value\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tto delete content from the 'tableName'\r\n" +
                "\texit\r\n" +
                "\t\tto exit from the program]", captor.getAllValues().toString());
    }
}