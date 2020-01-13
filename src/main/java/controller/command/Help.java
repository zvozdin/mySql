package controller.command;

import view.View;

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

        view.write("\tconnect|database|user|password");
        view.write("\t\tto connect to database");

        view.write("\tlist");
        view.write("\t\tto display a list of tables");

        view.write("\tfind|tableName");
        view.write("\t\tto retrieve content from the 'tableName'");

        view.write("\tclear|tableName");
        view.write("\t\tto delete content from the 'tableName'");

        view.write("\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN");
        view.write("\t\tto record content to the 'tableName'");

        view.write("\texit");
        view.write("\t\tto exit from the programm");
    }
}