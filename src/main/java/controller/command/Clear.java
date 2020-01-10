package controller.command;

import model.DatabaseManager;
import view.View;

import java.util.List;

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
        try {
            String[] data = command.split("\\|");
            String tableName = data[1];
            List<String> tables = manager.getTablesNames();
            for (String table : tables) {
                if (tableName.equals(table)) {
                    manager.clear(tableName);
                    view.write(String.format("Table '%s' is cleared!", tableName));
                    return;
                }
            }
            throw new IllegalArgumentException(String.format(
                    "Table '%s' doesn't exist.", tableName));
        } catch (Exception e) {
            printError(e);
        }
    }

    private void printError(Exception e) {
        String message = "" + e.getMessage();
        if (e.getCause() != null) {
            message += "\n" + e.getCause().getMessage();
        }
        view.write("Fail for a reason ==> " + message);
        view.write("Use command 'list' to look existing tables...");
    }
}
