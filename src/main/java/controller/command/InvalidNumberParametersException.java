package controller.command;

public class InvalidNumberParametersException extends IllegalArgumentException {

    public InvalidNumberParametersException(int command, int data, String commandSample) {
        throw new IllegalArgumentException(String.format("" +
                "Invalid number of parameters separated by '|'.\n" +
                "Expected %s. You enter ==> %s.\n" +
                "Use command '%s'", command, data, commandSample));
    }
}