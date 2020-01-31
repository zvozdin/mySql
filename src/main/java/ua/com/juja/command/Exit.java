package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.View;

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
        manager.closeConnection();
        view.write(ActionMessages.EXIT.toString());
        throw new ExitException();
    }
}