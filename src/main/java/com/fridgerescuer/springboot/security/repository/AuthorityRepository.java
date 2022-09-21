package com.fridgerescuer.springboot.security.repository;

import com.fridgerescuer.springboot.security.entity.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
