package com.fridgerescuer.springboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.fridgerescuer.springboot")
@RequiredArgsConstructor
public class Config {
    private final MongoDatabaseFactory mongoDatabaseFactory;
    private final MongoTemplate mongoTemplate;

    @Autowired
    private MappingMongoConverter mongoConverter;

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDatabaseFactory, mongoConverter);
    }
}
