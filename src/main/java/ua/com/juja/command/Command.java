package ua.com.juja.command;

public interface Command {

    boolean canProcess(String command);

    void process(String command);

    default void parametersNumberValidation(String sampleCommand, String inputCommand) {
        String[] correctCommand = sampleCommand.split("\\|");
        String[] data = inputCommand.split("\\|");
        if (correctCommand.length != data.length) {
            if (inputCommand.startsWith("create|") & data.length > correctCommand.length) {
                return;
            }
            throw new InvalidParametersNumberException(correctCommand.length, data.length, sampleCommand);
        }
    }
}