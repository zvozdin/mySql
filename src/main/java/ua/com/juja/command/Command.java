package ua.com.juja.command;

public interface Command {

    boolean canProcess(String command);

    void process(String command);

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
}