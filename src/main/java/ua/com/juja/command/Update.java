package ua.com.juja.command;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

public class Update implements Command {

    private static final String COMMAND_SAMPLE = "update|tableName|column1|value1|column2|value2";
    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(COMMAND_SAMPLE, command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        DataSet set = new DataSet();
        set.put(data[2], data[3]);

        DataSet where = new DataSet();
        where.put(data[4], data[5]);

        manager.update(tableName, set, where);

        view.write(String.format("Record '%s' updated.", where.getValues().get(0)));
        view.write(manager.getDataInTableFormat(tableName));
    }
}