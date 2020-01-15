package controller.command;

import model.DatabaseManager;
import view.View;

public class Disconnect implements Command {

    private DatabaseManager manager;
    private View view;

    public Disconnect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("disconnect");
    }

    @Override
    public void process(String command) {
        manager.disconnect();
        view.write("Database disconnected");
    }
}
