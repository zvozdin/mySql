package ua.com.juja.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "databaseConnection")
public class DatabaseConnection {

    @Id
    private String id;
    private String userName;
    private String database;

    public DatabaseConnection() {
    }

    @PersistenceConstructor
    public DatabaseConnection(String userName, String database) {
        this.userName = userName;
        this.database = database;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}