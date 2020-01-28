package ua.com.juja.command;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

public class Insert implements Command {

    private static final String COMMAND_SAMPLE = "insert|tableName|column|value";
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
        parametersNumberValidation(COMMAND_SAMPLE, command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        DataSet insert = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            insert.put(columnName, value);
        }

        manager.insert(tableName, insert);

        view.write(String.format("Record '%s' added.", insert.getValues()));
    }
}