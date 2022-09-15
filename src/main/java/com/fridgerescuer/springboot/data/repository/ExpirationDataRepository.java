package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpirationDataRepository extends MongoRepository<ExpirationData, String> {
}
