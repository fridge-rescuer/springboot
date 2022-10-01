package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesById(String id);
}
