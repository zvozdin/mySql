package ua.com.juja.service;

import org.springframework.stereotype.Component;
import ua.com.juja.model.entity.Description;

import java.util.Arrays;
import java.util.List;

@Component
public class ServiceImpl implements Service {

    @Override
    public List<String> getCommands() {
        return Arrays.asList(
                "help",
                "connect",
                "newDatabase",
                "dropDatabase",
                "tables",
                "newTable",
                "dropTable",
                "insert",
                "update",
                "delete",
                "clear"
        );
    }

    @Override
    public List<Description> getCommandsDescription() {
        return Arrays.asList(
                new Description("connect", "to connect to the database"),
                new Description("newDatabase", "to create a new database"),
                new Description("dropDatabase", "to delete the database"),
                new Description("tables", "to display a list of tables"),
                new Description("newTable", "to create a new table"),
                new Description("dropTable", "to delete the table"),
                new Description("insert", "to record content to the 'tableName'"),
                new Description("" +
                        "update", "to update the content in the 'tableName' " +
                        "set column1 = value1 where column2 = value2"),
                new Description("delete", "to delete content where column = value"),
                new Description("clear", "to delete content from the 'tableName'")
                );
    }
}