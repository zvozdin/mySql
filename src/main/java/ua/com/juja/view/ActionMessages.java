package ua.com.juja.view;

public enum ActionMessages {

    CLEAR("Table '%s' is cleared!"),
    CREATE("Table '%s' created."),
    CREATE_EXISTING_TABLE("Table '%s' already exists"),
    DELETE("Record '%s' deleted."),
    DATABASE_EXISTS("Database '%s' already exists"),
    DATABASE_NEW("Database '%s' created."),
    DROP("Table '%s' deleted."),
    DROP_DB("Database '%s' deleted."),
    EXIT("See you soon!"),
    INSERT("Record '%s' added."),
    NO_DRIVER("Cannot find the driver in the classpath!"),
    NO_CONNECTION("Can't get connection for database: %s, user: %s"),
    NOT_CONNECTED("You cannot use command '%s' until you connect to database. Use "),
    NOT_EXISTING_COMMAND("Not existing '%s' command."),
    NOT_EXISTING_DATABASE("Database '%s' doesn't exist"),
    NOT_EXISTING_TABLE("Table '%s' doesn't exist"),
    SUCCESS("Success!"),
    UPDATE("Record '%s' updated."),

    INVALID_NUMBER("" +
            "Invalid parameters number separated by '|'.\n" +
            "Expected %s. You enter ==> %s.\n" +
            "Use command '%s'"),
    INVALID_NUMBER_MIN("" +
            "Invalid parameters number separated by '|'.\n" +
            "Expected min %s. You enter ==> %s.\n" +
            "Use command "),
    INVALID_NUMBER_EVEN("" +
            "Invalid parameters number separated by '|'.\n" +
            "Expected even count. You enter ==> %s.\n" +
            "Use command ");

    private final String text;

    ActionMessages(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}