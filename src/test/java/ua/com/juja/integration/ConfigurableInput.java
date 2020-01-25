package ua.com.juja.integration;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurableInput extends InputStream {

    private String line = "";
    private boolean endLine;

    @Override
    public int read() throws IOException {
        if (line.length() == 0) {
            return -1;
        }

        if (endLine) {
            endLine = false;
            return -1;
        }

        char ch = line.charAt(0);
        line = line.substring(1);

        if (ch == '\n') {
            endLine = true;
        }

        return ch;
    }

    public void addCommand(String command) {
        if (line.length() == 0) {
            line = command;
        } else {
            line += "\n" + command;
        }
    }
}