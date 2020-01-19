package controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        assertEquals("[" +
                "Existing commands:, " +
                "\thelp, " +
                "\t\tto display a list of commands, " +
                "\tconnect|databaseName|user|password, " +
                "\t\tto connect to the database, " +
                "\tnewDatabase|databaseName, " +
                "\t\tto create a new database, " +
                "\tdropDatabase|databaseName, " +
                "\t\tto delete the database, " +
                "\tlist, " +
                "\t\tto display a list of tables, " +
                "\tcreate|tableName|column1|column2|...|columnN, " +
                "\t\tto create a new table, " +
                "\tdrop|tableName, " +
                "\t\tto delete the table, " +
                "\tfind|tableName, " +
                "\t\tto retrieve content from the 'tableName', " +
                "\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN, " +
                "\t\tto record content to the 'tableName', " +
                "\tupdate|tableName|column1|value1|column2|value2, " +
                "\t\tto update the content in the 'tableName', " +
                "\t\t\tset column1 = value1 where column2 = value2, " +
                "\tdelete|tableName|column|value, " +
                "\t\tto delete content where column = value, " +
                "\tclear|tableName, " +
                "\t\tto delete content from the 'tableName', " +
                "\texit, " +
                "\t\tto exit from the program]", captor.getAllValues().toString());
    }
}