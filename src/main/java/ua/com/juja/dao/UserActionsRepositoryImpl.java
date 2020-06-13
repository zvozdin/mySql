package ua.com.juja.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ua.com.juja.entity.DatabaseConnection;
import ua.com.juja.entity.UserAction;

public class UserActionsRepositoryImpl implements UserActionsRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DatabaseConnectionsRepository databaseConnections;

    @Override
    public void saveAction(String action, String user, String database) {
        DatabaseConnection databaseConnection = databaseConnections.findByUserNameAndDatabase(user, database);
        if (databaseConnection == null) {
            databaseConnection = databaseConnections.save(new DatabaseConnection(user, database));
        }

        UserAction userAction = new UserAction(action, databaseConnection);
        mongoTemplate.save(userAction);
    }
}