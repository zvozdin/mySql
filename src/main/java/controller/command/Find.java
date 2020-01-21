package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.TableGenerator;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class Find implements Command {

    private static final String COMMAND_SAMPLE = "find|tableName";
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
        parametersNumberValidation(COMMAND_SAMPLE, command);

        String[] data = command.split("\\|");

        String tableName = data[1];

        printTable(tableName);
    }

    void printTable(String tableName) {
        TableGenerator tableGenerator = new TableGenerator();

        List<String> columns = manager.getTableColumns(tableName);
        List<DataSet> objectRows = manager.getTableData(tableName);

        List<List<String>> stringRows = new ArrayList<>();
        for (DataSet row : objectRows) {
            List<String> stringRow = new ArrayList<>();
            for (Object value : row.getValues()) {
                stringRow.add(value.toString());
            }
            stringRows.add(stringRow);
        }

        view.write(tableGenerator.generateTable(columns, stringRows));
    }
}