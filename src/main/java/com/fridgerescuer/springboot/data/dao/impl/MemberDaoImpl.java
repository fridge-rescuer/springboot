package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private final MemberRepository repository;

    @Autowired
    private final MongoTemplate template;

    @Override
    public Member saveMember(Member member) {
        return repository.save(member);
    }

    @Override
    public Member findById(String memberId) {
        Optional<Member> findMember = repository.findById(memberId);

        if(findMember.isEmpty()){
            log.debug("can't find member by this id ={}", memberId);
            return null;
        }

        return findMember.get();
    }

    @Override
    public void addIngredientsToMember(String memberId, List<Ingredient> ingredients) {

        if(ingredients.size() == 0){
            log.debug("ingredients param size ={}", ingredients.size());
            return;
        }

        if(ingredients.get(0).getId() == null){
            log.debug("ingredients ids are null ");
            return;
        }

        template.update(Member.class)
                .matching(where("id").is(memberId))
                .apply(new Update().set("ingredients",ingredients))
                .first();
    }
}
