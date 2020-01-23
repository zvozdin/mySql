package ua.com.juja.controller.command;

import ua.com.juja.view.View;

public class Help implements Command {

    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("Existing commands:");

        view.write("\thelp");
        view.write("\t\tto display a list of commands");

        view.write("\tconnect|databaseName|user|password");
        view.write("\t\tto connect to the database");

        view.write("\tnewDatabase|databaseName");
        view.write("\t\tto create a new database");

        view.write("\tdropDatabase|databaseName");
        view.write("\t\tto delete the database");

        view.write("\tlist");
        view.write("\t\tto display a list of tables");

        view.write("\tcreate|tableName|column1|column2|...|columnN");
        view.write("\t\tto create a new table");

        view.write("\tdrop|tableName");
        view.write("\t\tto delete the table");

        view.write("\tfind|tableName");
        view.write("\t\tto retrieve content from the 'tableName'");

        view.write("\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN");
        view.write("\t\tto record content to the 'tableName'");

        view.write("\tupdate|tableName|column1|value1|column2|value2");
        view.write("\t\tto update the content in the 'tableName'");
        view.write("\t\t\tset column1 = value1 where column2 = value2");

        view.write("\tdelete|tableName|column|value");
        view.write("\t\tto delete content where column = value");

        view.write("\tclear|tableName");
        view.write("\t\tto delete content from the 'tableName'");

        view.write("\texit");
        view.write("\t\tto exit from the program");
    }
}