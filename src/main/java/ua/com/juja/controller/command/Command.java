package ua.com.juja.controller.command;

public interface Command {

    boolean canProcess(String command);

    void process(String command);

    default void parametersNumberValidation(String sampleCommand, String inputCommand) {
        String[] correctCommand = sampleCommand.split("\\|");
        String[] data = inputCommand.split("\\|");
        if (correctCommand.length != data.length) {
            throw new InvalidParametersNumberException(correctCommand.length, data.length, sampleCommand);
        }
    }
}