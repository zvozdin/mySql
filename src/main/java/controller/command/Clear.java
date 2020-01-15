package controller.command;

import model.DatabaseManager;
import view.View;

import java.util.List;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;
    private static final String COMMAND_SAMPLE = "clear|tableName";

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String[] commandToClear = COMMAND_SAMPLE.split("\\|");
        if (data.length != commandToClear.length) {
            throw new IllegalArgumentException(String.format(
                    "Invalid number of parameters separated by '|'. Expected %s. You enter ==> %s",
                    commandToClear.length, data.length));
        }
        String tableName = data[1];
        List<String> tables = manager.getTablesNames();
        for (String table : tables) {
            if (tableName.equals(table)) {
                manager.clear(tableName);
                view.write(String.format("Table '%s' is cleared!", tableName));
                return;
            }
        }
        // TODO create Exception class to throw the same message for all Commands when user enters non existed table
        // TODO or remain here throwing and catch in MainController bi printError() method
        throw new IllegalArgumentException(String.format(
                "Table '%s' doesn't exist.", tableName));
    }
}