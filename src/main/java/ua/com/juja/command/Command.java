package ua.com.juja.command;

public interface Command {

    boolean canProcess(String command);

    void process(String command);

    default void parametersNumberValidation(String sample, String input) {
        String[] sampleArr = sample.split("\\|");
        String[] inputArr = input.split("\\|");
        if (sampleArr.length != inputArr.length) {
            if (input.startsWith("create|") && inputArr.length > sampleArr.length) {
                return;
            }

            if (input.startsWith("insert|") && inputArr.length > sampleArr.length && inputArr.length % 2 == 0) {
                return;
            }

            throw new InvalidParametersNumberException(sampleArr.length, inputArr.length, sample);
        }
    }
}