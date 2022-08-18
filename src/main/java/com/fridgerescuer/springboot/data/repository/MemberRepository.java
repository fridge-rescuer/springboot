package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member, String> {
}
