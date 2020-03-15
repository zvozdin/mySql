package ua.com.juja.command;

import ua.com.juja.model.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public interface Command {

    // TODO delete method
    boolean canProcess(String command);

    // TODO delete method
    void process(String command);

    void processWeb(DatabaseManager manager, String name, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    default void parametersNumberValidation(String sample, String input) {
        String[] sampleParameters = sample.split("\\|");
        String[] inputParameters = input.split("\\|");
        if (sampleParameters.length == inputParameters.length ||
            input.startsWith("create|") && inputParameters.length > sampleParameters.length ||
            input.startsWith("insert|") && inputParameters.length > sampleParameters.length && inputParameters.length % 2 == 0)
        {
            return;
        }

        throw new InvalidParametersNumberException(sampleParameters.length, inputParameters.length, sample);
    }

    default Map<String, String> getCommandParameters(String[] data) {
        Map<String, String> command = new LinkedHashMap<>();
        for (int index = 1; index < data.length / 2; index++) {
            String column = data[index * 2];
            String value = data[index * 2 + 1];
            command.put(column, value);
        }

        return command;
    }
}