package com.fridgerescuer.springboot.secu.repository;

import com.fridgerescuer.springboot.secu.entity.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
