package ua.com.juja.model;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.juja.model.entity.DatabaseConnection;
import ua.com.juja.model.entity.UserAction;

public class UserActionsRepositoryImpl implements UserActionsRepositoryCustom {

    @Autowired
    private UserActionsRepository userActions;

    @Autowired
    private DatabaseConnectionsRepository databaseConnections;

    @Override
    public void saveAction(String action, String user, String database) {
        DatabaseConnection databaseConnection = databaseConnections.findByUserNameAndDbName(user, database);
        if (databaseConnection == null) {
            databaseConnection = databaseConnections.save(new DatabaseConnection(user, database));
        }
        userActions.save(new UserAction(action, databaseConnection));
    }
}