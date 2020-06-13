package ua.com.juja.dao;

public interface UserActionsRepositoryCustom {

    void saveAction(String action, String user, String database);
}