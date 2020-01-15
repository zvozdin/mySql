package controller.command;

import model.DatabaseManager;
import view.View;

public class DeleteRow implements Command {

    private DatabaseManager manager;
    private View view;

    public DeleteRow(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }
}
