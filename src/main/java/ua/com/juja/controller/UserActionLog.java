package ua.com.juja.controller;

import ua.com.juja.entity.UserAction;

public class UserActionLog {

    private String userName;
    private String database;
    private String action;

    public UserActionLog() {
        // do nothing
    }

    public UserActionLog(UserAction action) {
        this.userName = action.getDatabaseConnection().getUserName();
        this.database = action.getDatabaseConnection().getDatabase();
        this.action = action.getAction();
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "userName = " + userName + ", " +
                "database = " + database + ", " +
                "action = " + action;
    }
}