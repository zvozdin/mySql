package controller.command;

import model.DatabaseManager;
import view.View;

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
        commandValidation(COMMAND_SAMPLE, command);
        String[] data = command.split("\\|");
//        String[] commandToClear = COMMAND_SAMPLE.split("\\|");
//        if (data.length != commandToClear.length) {
//            throw new InvalidParametersNumberException(commandToClear.length, data.length, COMMAND_SAMPLE);
//        }

        String tableName = data[1];
        manager.clear(tableName);
        view.write(String.format("Table '%s' is cleared!", tableName));
    }
}