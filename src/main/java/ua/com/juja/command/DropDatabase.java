package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DropDatabase implements Command {

    private DatabaseManager manager;
    private View view;

    public DropDatabase(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropDatabase|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.DROP_DB.toString(), command);

        String[] data = command.split("\\|");
        String databaseName = data[1];

        manager.dropDatabase(databaseName);

        view.write(String.format(ActionMessages.DROP_DB.toString(), databaseName));
    }

    @Override
    public void processWeb(DatabaseManager manager, String name, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}