package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;
import ua.com.juja.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DropTable implements Command {

    private DatabaseManager manager;
    private View view;

    public DropTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public DropTable() {

    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void process(String command) {
        parametersNumberValidation(CommandSamples.DROP.toString(), command);

        String[] data = command.split("\\|");
        String tableName = data[1];

        manager.dropTable(tableName);

        view.write(String.format(ActionMessages.DROP.toString(), tableName));
    }

    @Override
    public void processWeb(DatabaseManager manager, String tableName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        manager.dropTable(tableName);

        req.setAttribute("report", String.format(ActionMessages.DROP.toString(), tableName));
    }

    @Override
    public String toString() {
        return "dropTable";
    }
}