package controller.command;

import model.DatabaseManager;
import view.View;

public class DropTable implements Command {

    private static final String COMMAND_SAMPLE = "drop|tableName";
    private DatabaseManager manager;
    private View view;

    public DropTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(COMMAND_SAMPLE, command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        manager.dropTable(tableName);
        view.write(String.format("Table '%s' deleted.", tableName));
    }
}