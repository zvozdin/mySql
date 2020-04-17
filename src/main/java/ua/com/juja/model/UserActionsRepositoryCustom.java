package ua.com.juja.model;

public interface UserActionsRepositoryCustom {

    void saveAction(String action, String user, String database);
}