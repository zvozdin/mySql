package ua.com.juja.command;

import ua.com.juja.view.View;

public class Unsupported implements Command {

    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write(String.format("Not existing '%s' command.", command));
    }
}