package ua.com.juja.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.juja.model.entity.DatabaseConnection;

@Repository
public interface DatabaseConnectionsRepository extends CrudRepository<DatabaseConnection, Integer> {

    DatabaseConnection findByUserNameAndDatabase(String userName, String database);
}