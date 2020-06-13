package ua.com.juja.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userAction")
public class UserAction {

    @Id
    private String id;
    private String action;

    @DBRef(db = "databaseConnection")
    public DatabaseConnection databaseConnection;

    public UserAction() {
    }

    @PersistenceConstructor
    public UserAction(String action, DatabaseConnection databaseConnection) {
        this.action = action;
        this.databaseConnection = databaseConnection;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}