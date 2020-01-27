package ua.com.juja.command;

public class InvalidParametersNumberException extends IllegalArgumentException {

    public InvalidParametersNumberException(int command, int data, String commandSample) {
        String start = "Invalid parameters number separated by '|'.\n";
        String end = "You enter ==> %s.\nUse command '%s'";
        if (commandSample.startsWith("create|")) {
            throw new IllegalArgumentException(String.format(
                    start + "Expected no less than %s. " + end,
                    command, data, commandSample));
        }
        throw new IllegalArgumentException(String.format(
                start + "Expected %s. " + end,
                command, data, commandSample));
    }
}