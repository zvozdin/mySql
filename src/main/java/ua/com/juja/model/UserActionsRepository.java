package ua.com.juja.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.juja.model.entity.UserAction;

import java.util.List;

@Repository
public interface UserActionsRepository extends MongoRepository<UserAction, Integer>, UserActionsRepositoryCustom {

    @Query("{'databaseConnection.$id': ?0}")
    List<UserAction> findByDatabaseConnectionId(ObjectId id);
}