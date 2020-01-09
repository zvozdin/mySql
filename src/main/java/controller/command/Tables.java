package controller.command;

import model.DatabaseManager;
import view.View;

import java.util.List;

public class Tables implements Command {

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        List tablesNames = manager.getTablesNames();
        view.write(tablesNames.toString());
    }
}
