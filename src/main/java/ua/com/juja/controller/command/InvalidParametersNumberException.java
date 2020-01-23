package ua.com.juja.controller.command;

public class InvalidParametersNumberException extends IllegalArgumentException {

    public InvalidParametersNumberException(int command, int data, String commandSample) {
        throw new IllegalArgumentException(String.format("" +
                "Invalid parameters number separated by '|'.\n" +
                "Expected %s. You enter ==> %s.\n" +
                "Use command '%s'", command, data, commandSample));
    }
}