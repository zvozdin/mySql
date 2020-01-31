package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeleteRow implements Command {

    private DatabaseManager manager;
    private View view;

    public DeleteRow(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.DELETE.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        Map<String, String> delete = new LinkedHashMap<>();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            delete.put(columnName, value);
        }

        manager.deleteRow(tableName, delete);

        view.write(String.format(ActionMessages.DELETE.toString(), delete.values().iterator().next()));
        view.write(manager.getTableFormatData(tableName));
    }
}