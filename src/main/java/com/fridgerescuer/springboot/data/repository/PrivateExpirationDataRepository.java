package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivateExpirationDataRepository extends MongoRepository<PrivateExpirationData, String> {
}
