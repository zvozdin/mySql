package ua.com.juja.service;

import ua.com.juja.command.Command;
import ua.com.juja.model.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public interface Service {

    List<Command> commands();

    DatabaseManager connect(String database, String user, String password);

    void newDatabase(DatabaseManager manager, String databaseName);

    void dropDatabase(DatabaseManager manager, String databaseName);

    List<List<String>> find(DatabaseManager manager, String tableName);

    void clear(DatabaseManager manager, String tableName);

    default List<List<String>> getTableData(DatabaseManager manager, String tableName) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(new ArrayList<>(manager.getColumns(tableName)));
        rows.addAll(manager.getRows(tableName));

        return rows;
    }
}