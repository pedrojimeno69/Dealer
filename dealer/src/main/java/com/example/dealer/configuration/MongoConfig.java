package com.example.dealer.configuration;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.dealer.repository.mongo")
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, "localhost", 27017)), "test");
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.example.dealer.repository.mongo");
    }
}
