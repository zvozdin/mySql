package ua.com.juja.model.entity;

public class Description {

    private String command;
    private String description;

    public Description(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return command + '=' + description;
    }
}