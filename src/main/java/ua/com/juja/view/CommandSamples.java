package ua.com.juja.view;

public enum CommandSamples {

    CLEAR("clear|tableName"),
    CONNECT("connect|database|user|password"),
    CREATE("create|tableName|columnName"),
    DELETE("delete|tableName|column|value"),
    DROP("drop|tableName"),
    DROP_DB("dropDatabase|databaseName"),
    FIND("find|tableName"),
    INSERT("insert|tableName|column|value"),
    NEWDATABASE("newDatabase|databaseName"),
    UPDATE("update|tableName|column1|value1|column2|value2"),
    USE_CREATE("'create|tableName|column1|column2|...|columnN'"),
    USE_INSERT("'insert|tableName|column1|value1|column2|value2|...|columnN|valueN'");

    private final String text;

    CommandSamples(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}