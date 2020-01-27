package ua.com.juja.command;

public class InvalidParametersNumberException extends IllegalArgumentException {

    public InvalidParametersNumberException(int sample, int input, String sampleTxt) {
        String start = "Invalid parameters number separated by '|'.\n";
        String end = "You enter ==> %s.\n";

        if (sampleTxt.startsWith("create|")) {
            String command = "Use command 'create|tableName|column1|column2|...|columnN'";
            throw new IllegalArgumentException(String.format(
                    start + "" +
                            "Expected min %s. " + end + "" +
                            command, sample, input));
        }

        if (sampleTxt.startsWith("insert|")) {
            String command = "Use command 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'";
            if (input < sample) {
                throw new IllegalArgumentException(String.format( //TODO extract samples with 'N' into enum not only Command_Sample
                        start + "" +                              // TODO or extract all whole message
                                "Expected min %s. " + end + "" +
                                command, sample, input));
            } else {
                throw new IllegalArgumentException(String.format( //TODO extract samples with 'N' into enum not only Command_Sample
                        start + "" +                              // TODO or extract all whole message
                                "Expected even count. " + end + "" +
                                command, input));
            }
        }

        throw new IllegalArgumentException(String.format(
                start + "" +
                        "Expected %s. " + end + "" +
                        "Use command '%s'", sample, input, sampleTxt));
    }
}