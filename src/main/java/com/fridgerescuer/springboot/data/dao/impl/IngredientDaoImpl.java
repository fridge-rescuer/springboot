package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchIngredientException;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component  //빈으로 등록되어 다른 클래스가 인터페이스로 의존성 주입 받을 때 자동 등록됨
@RequiredArgsConstructor
public class IngredientDaoImpl implements IngredientDao {

    @Autowired
    private final IngredientRepository repository;
    @Autowired
    private final MongoTemplate template;

    @Override
    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @Override
    public Ingredient find(Ingredient ingredient) { //이름으로 찾기
        return repository.findByName(ingredient.getName());
    }

    @Override
    public Ingredient findByName(String name) {
        Ingredient findIngredient = repository.findByName(name);
        if(findIngredient ==null){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient name in Repository, name=" + name));
        }

        return repository.findByName(name);
    }

    @Override
    public Ingredient findById(String id) {
        Optional<Ingredient> findIngredient = repository.findById(id);

        if(findIngredient.isEmpty()){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient id in Repository, id=" + id));
        }

        return findIngredient.get();
    }

    @Override
    public void update(String targetId, Ingredient ingredient) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetId));

        Update update = new Update();
        update.set("name", ingredient.getName());
        update.set("type", ingredient.getType());
        UpdateResult updateResult = template.updateMulti(query, update, Ingredient.class);
        //return updateResult.
    }
}
