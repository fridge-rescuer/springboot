package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchIngredientException;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchMemberException;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private final MemberRepository repository;

    @Autowired
    private final MongoTemplate template;

    @Override
    public Member saveMember(Member member) {
        log.info("save Member ={}", member);
        return repository.save(member);
    }

    @Override
    public Member findById(String memberId) {
        Optional<Member> findMember = repository.findById(memberId);

        if(findMember.isEmpty()){
            throw new NoSuchMemberException(new NullPointerException("no such member id in Repository, id=" + memberId));
        }

        return findMember.get();
    }

    @Override
    public void addIngredientsToMember(String memberId, List<Ingredient> ingredients) {

        if(ingredients.size() == 0){    //추가하는 재료가 비어있는 경우
            log.debug("ingredients param size ={}", ingredients.size());
            return;
        }

        if(ingredients.get(0).getId() == null){ //재료의 id가 존재하지 않는 경우
            throw new NoSuchIngredientException(new NullPointerException("no such ingredient id"));
        }

        template.update(Member.class)
                .matching(where("id").is(memberId))
                .apply(new Update().set("ingredients",ingredients))
                .first();

        log.info("add ingredient size={}, to member id={}", ingredients.size(),memberId);
    }

    @Override
    public void addRecipeToMember(String memberId, Recipe recipe) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));

        Update update = new Update();
        update.push("recipes",recipe);

        UpdateResult updateResult = template.updateMulti(query, update, Member.class);

        if(updateResult.getModifiedCount() == 0){ //write 가 실패한 경우
            throw new NoSuchMemberException(new NullPointerException("no such member id in Repository, id=" + memberId));
        }

        log.info("add recipe ={}, to member id={}", recipe,memberId);
    }

    @Override
    public void updateMemberById(String memberId, MemberDTO updateDataMemberDTO) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));

        Update update = new Update();
        update.set("name",updateDataMemberDTO.getName());

        UpdateResult updateResult = template.updateMulti(query, update, Member.class);

        if(updateResult.getModifiedCount() == 0){ //write 가 실패한 경우
            throw new NoSuchMemberException(new NullPointerException("no such member id in Repository, id=" + memberId));
        }

        log.info("updateDataMemberDTO ={}", updateDataMemberDTO);
    }

    @Override
    public void deleteMemberById(String memberId) {
        this.findById(memberId);    //존재하지 않는 id의 경우 예외 발생

        repository.deleteById(memberId);
        log.info("delete Member id ={}", memberId);
    }

}
