package ua.com.juja.command;

import ua.com.juja.view.ActionMessages;
import ua.com.juja.view.CommandSamples;

public class InvalidParametersNumberException extends IllegalArgumentException {

    public InvalidParametersNumberException(int sample, int input, String sampleTxt) {
        if (sampleTxt.startsWith("create|")) {
            throw new IllegalArgumentException(String.format(
                    ActionMessages.INVALID_NUMBER_MIN.toString() + CommandSamples.USE_CREATE.toString(),
                    sample, input));
        }

        if (sampleTxt.startsWith("insert|")) {
            if (input < sample) {
                throw new IllegalArgumentException(String.format(
                        ActionMessages.INVALID_NUMBER_MIN.toString() + CommandSamples.USE_INSERT.toString(),
                        sample, input));
            } else {
                throw new IllegalArgumentException(String.format(
                        ActionMessages.INVALID_NUMBER_EVEN.toString() + CommandSamples.USE_INSERT.toString(),
                        input));
            }
        }

        throw new IllegalArgumentException(String.format(
                ActionMessages.INVALID_NUMBER.toString(),
                sample, input, sampleTxt));
    }
}