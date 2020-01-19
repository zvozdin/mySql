package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

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
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected even count. You enter ==> %s.\n" +
                    "Use command 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'", data.length));
        }

        String[] commandToInsert = COMMAND_SAMPLE.split("\\|");
        if (data.length < commandToInsert.length) {
            throw new IllegalArgumentException(String.format("" +
                    "Invalid parameters number separated by '|'.\n" +
                    "Expected min %s. You enter ==> %s.\n" +
                    "Use command 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'",
                    commandToInsert.length, data.length));
        }

        DataSet insert = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            insert.put(columnName, value);
        }

        String tableName = data[1];
        manager.insert(tableName, insert);
        view.write(String.format("Record '%s' added.", insert.getValues()));
        // print table with values
        new Find(manager, view).printTableHeader(tableName);
        new Find(manager, view).printValues(tableName);
    }
}