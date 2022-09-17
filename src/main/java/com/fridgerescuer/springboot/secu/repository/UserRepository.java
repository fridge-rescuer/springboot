package com.fridgerescuer.springboot.secu.repository;

import com.fridgerescuer.springboot.secu.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
