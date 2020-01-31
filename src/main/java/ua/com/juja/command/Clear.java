package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;

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
        parametersNumberValidation(CommandSamples.CLEAR.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        manager.clear(tableName);
        view.write(String.format(ActionMessages.CLEAR.toString(), tableName));
    }
}