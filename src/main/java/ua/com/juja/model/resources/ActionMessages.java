package ua.com.juja.model.resources;

public enum ActionMessages {

    CLEAR("Table '%s' is cleared!"),
    CREATE("Table '%s' is created."),
    CREATE_EXISTING_TABLE("Error! Table '%s' already exists"),
    DELETE("Record '%s' is deleted."),
    DATABASE_EXISTS("Error! Database '%s' already exists"),
    DATABASE_NEW("Database '%s' is created."),
    DROP("Table '%s' is deleted."),
    DROP_DB("Database '%s' is deleted."),
    INSERT("Record '%s' is added."),
    NO_DRIVER("Cannot find the driver in the classpath!"),
    NO_CONNECTION("Can't get connection for database: %s, user: %s"),
    NOT_EXISTING_DATABASE("Database '%s' doesn't exist"),
    NOT_EXISTING_TABLE("Table '%s' doesn't exist"),
    UPDATE("Record '%s' is updated.");

    private final String text;

    ActionMessages(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}