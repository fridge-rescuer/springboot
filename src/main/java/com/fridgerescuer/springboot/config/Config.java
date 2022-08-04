package com.fridgerescuer.springboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class Config {
    private final MongoDatabaseFactory mongoDatabaseFactory;
    private final MongoTemplate mongoTemplate;
}
