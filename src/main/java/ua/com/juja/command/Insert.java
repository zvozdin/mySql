package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import java.util.Map;

public class Insert implements Command {

    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.INSERT.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        Map<String, String> insert = getCommandParameters(data);

        manager.insert(tableName, insert);

        view.write(String.format(ActionMessages.INSERT.toString(), insert.values()));
    }
}