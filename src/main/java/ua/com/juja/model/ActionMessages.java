package ua.com.juja.model;

public enum ActionMessages {

    CLEAR("Table '%s' is cleared!"),
    CREATE("Table '%s' created."),
    CREATE_EXISTING_TABLE("Table '%s' already exists"),
    DELETE("Record '%s' deleted."),
    DATABASE_EXISTS("Database '%s' already exists"),
    DATABASE_NEW("Database '%s' created."),
    DROP("Table '%s' deleted."),
    DROP_DB("Database '%s' deleted."),
    INSERT("Record '%s' added."),
    NO_DRIVER("Cannot find the driver in the classpath!"),
    NO_CONNECTION("Can't get connection for database: %s, user: %s"),
    NOT_EXISTING_DATABASE("Database '%s' doesn't exist"),
    NOT_EXISTING_TABLE("Table '%s' doesn't exist"),
    UPDATE("Record '%s' updated.");

    private final String text;

    ActionMessages(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}