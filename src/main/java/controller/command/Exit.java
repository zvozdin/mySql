package controller.command;

import model.DatabaseManager;
import view.View;

public class Exit implements Command {

    private DatabaseManager manager;
    private View view;

    public Exit(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        // TODO manager.closeConnectionWithMySQSL(); after disconnect command need connection to exit from app
        view.write("See you soon!");
        throw new ExitException();
    }
}