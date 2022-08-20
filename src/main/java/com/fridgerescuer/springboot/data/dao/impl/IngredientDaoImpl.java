package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchIngredientException;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchRecipeException;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Component  //빈으로 등록되어 다른 클래스가 인터페이스로 의존성 주입 받을 때 자동 등록됨
@RequiredArgsConstructor
public class IngredientDaoImpl implements IngredientDao {

    @Autowired
    private final IngredientRepository repository;
    @Autowired
    private final MongoTemplate template;

    @Override
    public Ingredient save(Ingredient ingredient) {
        log.info("save ingredient ={}",ingredient);
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
    public List<Ingredient> findAllByCategory(String category) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*" + category + ".*"));    //name이 포함된 모든 이름에 대해 검색

        List<Ingredient> foundIngredients = template.find(query, Ingredient.class);

        if(foundIngredients.isEmpty()){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient category in Repository, category=" + category));
        }

        return foundIngredients;
    }

    @Override
    public void update(String targetId, Ingredient ingredient) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetId));

        Update update = new Update();
        update.set("name", ingredient.getName());
        //update.set("type", ingredient.getType());
        UpdateResult updateResult = template.updateMulti(query, update, Ingredient.class);

        log.info("update id={} to ingredient ={}", targetId,ingredient);

        if (updateResult.getModifiedCount() ==0){   //아무것도 변경되지 않은 경우, 해당 id가 존재하지 않는 것
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient id in Repository id=" + targetId));
        }

    }

    @Override
    public void delete(String targetId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetId));

        Ingredient removedIngredient = template.findAndRemove(query, Ingredient.class);
        log.info("delete ingredient ={}", removedIngredient);

        if(removedIngredient ==null){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient name in Repository, id=" + targetId));
        }

    }

}
