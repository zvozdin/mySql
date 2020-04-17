package ua.com.juja.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.model.entity.DatabaseConnection;
import ua.com.juja.model.entity.UserAction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserActionsRepositoryImpl implements UserActionsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseConnectionsRepository databaseConnections;

    @Override
    @Transactional(propagation = Propagation.REQUIRED )
    public void saveAction(String action, String user, String database) {
        DatabaseConnection databaseConnection = databaseConnections.findByUserNameAndDbName(user, database);
        if (databaseConnection == null) {
            databaseConnection = databaseConnections.save(new DatabaseConnection(user, database));
        }
        entityManager.persist(new UserAction(action, databaseConnection));
    }
}