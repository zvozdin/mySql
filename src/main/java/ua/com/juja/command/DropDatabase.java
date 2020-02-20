package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DropDatabase implements Command {

    private DatabaseManager manager;
    private View view;

    public DropDatabase(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public DropDatabase() {
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
    public void processWeb(DatabaseManager manager, String databaseName, HttpServletRequest req, HttpServletResponse resp)
    {
        manager.dropDatabase(databaseName);
        req.setAttribute("report", String.format(ActionMessages.DROP_DB.toString(), databaseName));
    }

    @Override
    public String toString() {
        return "dropDatabase";
    }
}