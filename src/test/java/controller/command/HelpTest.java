package controller.command;

import org.junit.Before;
import org.junit.Test;
import view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        verify(view).write("Existing commands:");

        verify(view).write("\thelp");
        verify(view).write("\t\tto display a list of commands");

        verify(view).write("\tconnect|databaseName|user|password");
        verify(view).write("\t\tto connect to the database");

        verify(view).write("\tnewDatabase|databaseName");
        verify(view).write("\t\tto create a new database");

        verify(view).write("\tdropDatabase|databaseName");
        verify(view).write("\t\tto delete the database");

        verify(view).write("\tlist");
        verify(view).write("\t\tto display a list of tables");

        verify(view).write("\tcreate|tableName|column1|column2|...|columnN");
        verify(view).write("\t\tto create a new table");

        verify(view).write("\tdrop|tableName");
        verify(view).write("\t\tto delete the table");

        verify(view).write("\tfind|tableName");
        verify(view).write("\t\tto retrieve content from the 'tableName'");

        verify(view).write("\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN");
        verify(view).write("\t\tto record content to the 'tableName'");

        verify(view).write("\tupdate|tableName|column1|value1|column2|value2");
        verify(view).write("\t\tto update the content in the 'tableName'");
        verify(view).write("\t\t\tset column1 = value1 where column2 = value2");

        verify(view).write("\tdelete|tableName|column|value");
        verify(view).write("\t\tto delete content where column = value");

        verify(view).write("\tclear|tableName");
        verify(view).write("\t\tto delete content from the 'tableName'");

        verify(view).write("\texit");
        verify(view).write("\t\tto exit from the program");
    }
}