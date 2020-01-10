package integration;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurableInput extends InputStream {

    private String line;
    private boolean endline;

    @Override
    public int read() throws IOException {
        if (line.length() == 0) {
            return -1;
        }

        if (endline) {
            endline = false;
            return -1;
        }

        char ch = line.charAt(0);
        line = line.substring(1);

        if (ch == '\n') {
            endline = true;
        }

        return ch;
    }

    public void addCommand(String command) {
        if (line == null) {
            line = command;
        } else {
            line += "\n" + command;
        }
    }
}
