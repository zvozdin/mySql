package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.View;

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
        commandValidation(COMMAND_SAMPLE, command);

//        String[] commandToConnect = COMMAND_SAMPLE.split("\\|");
//        if (data.length != commandToConnect.length) {
//            throw new InvalidNumberParametersException(commandToConnect.length, data.length, COMMAND_SAMPLE);
//        }

        String[] data = command.split("\\|");
        String tableName = data[1];
        printTableHeader(tableName);  // TODO print nice table
        printValues(tableName);
    }

    void printTableHeader(String tableName) {
        List<String> columns = manager.getTableColumns(tableName);
        String result = "|";
        for (String name : columns) {
            result += name + "|";
        }
        view.write("========================");
        view.write(result);
        view.write("========================");
    }

    void printValues(String tableName) {
        List<DataSet> users = manager.getTableData(tableName);
        for (DataSet row : users) {
            List<Object> values = row.getValues();
            String result = "|";
            for (Object element : values) {
                result += element + "|";
            }
            view.write(result);
        }
    }
}