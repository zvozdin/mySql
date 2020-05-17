package ua.com.juja.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.com.juja.model.entity.DatabaseConnection;

import java.util.List;

public interface DatabaseConnectionsRepository extends MongoRepository<DatabaseConnection, Integer> {

    DatabaseConnection findByUserNameAndDatabase(String userName, String database);

    List<DatabaseConnection> findByUserName(String userName);
}