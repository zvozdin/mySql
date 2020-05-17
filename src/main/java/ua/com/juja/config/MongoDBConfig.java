package ua.com.juja.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("ua.com.juja")
@PropertySource("classpath:log.mongo.properties")
public class MongoDBConfig {

    @Value("${log.mongo.databaseName}")
    private String databaseName;

    @Value("${log.mongo.port}")
    private int port;

    @Value("${log.mongo.host}")
    private String host;

    public @Bean MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient(host, port), databaseName);
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}