package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateDatabase implements Command {

    private DatabaseManager manager;
    private View view;

    public CreateDatabase(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public CreateDatabase() {
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("newDatabase|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.NEWDATABASE.toString(), command);

        String[] data = command.split("\\|");
        String databaseName = data[1];

        manager.createDatabase(databaseName);

        view.write(String.format(ActionMessages.DATABASE_NEW.toString(), databaseName));
    }

    @Override
    public void processWeb(DatabaseManager manager, String databaseName, HttpServletRequest req, HttpServletResponse resp)
    {
        manager.createDatabase(databaseName);
        req.setAttribute("report", String.format(ActionMessages.DATABASE_NEW.toString(), databaseName));
    }

    @Override
    public String toString() {
        return "newDatabase";
    }
}