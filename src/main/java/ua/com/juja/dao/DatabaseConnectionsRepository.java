package ua.com.juja.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.com.juja.entity.DatabaseConnection;

import java.util.List;

public interface DatabaseConnectionsRepository extends MongoRepository<DatabaseConnection, Integer> {

    DatabaseConnection findByUserNameAndDatabase(String userName, String database);

    List<DatabaseConnection> findByUserName(String userName);
}