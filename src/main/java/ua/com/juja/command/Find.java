package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.TableGenerator;
import ua.com.juja.view.View;

public class Find implements Command {

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.FIND.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        view.write(new TableGenerator()
                .generateTable(manager.getColumns(tableName), manager.getRows(tableName)));
    }
}