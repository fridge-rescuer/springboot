package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.data.mapper.RecipeMapper;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.exception.exceptionimpl.NoSuchIngredientException;
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

    private final IngredientMapper ingredientMapper = IngredientMapper.INSTANCE;
    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;

    @Override
    public void checkExistingIngredientId(String ingredientId) {
        Optional<Ingredient> foundIngredient = repository.findById(ingredientId);

        if(foundIngredient.isEmpty()){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient id in Repository, id=" + ingredientId));
        }
    }

    @Override
    public IngredientDTO save(IngredientDTO ingredientDTO) {
        Ingredient savedIngredient = repository.save(ingredientMapper.DTOtoIngredient(ingredientDTO));

        log.info("save ingredient ={}",savedIngredient);
        return ingredientMapper.ingredientToDTO(savedIngredient);
    }


    @Override
    public IngredientDTO findByName(String name) {
        Ingredient foundIngredient = repository.findByName(name);
        if(foundIngredient ==null){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient name in Repository, name=" + name));
        }

        return ingredientMapper.ingredientToDTO(foundIngredient);
    }

    private Ingredient getIngredientById(String id){
        Optional<Ingredient> foundIngredient = repository.findById(id);

        if(foundIngredient.isEmpty()){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient id in Repository, id=" + id));
        }

        return foundIngredient.get();
    }

    @Override
    public IngredientDTO findById(String id) {
        Ingredient foundIngredient = getIngredientById(id);

        return ingredientMapper.ingredientToDTO(foundIngredient);
    }

    @Override
    public List<IngredientDTO> findAllByContainName(String containName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*" + containName + ".*"));    //name이 포함된 모든 이름에 대해 검색

        List<Ingredient> foundIngredients = template.find(query, Ingredient.class);

        if(foundIngredients.isEmpty()){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient containName in Repository, containName=" + containName));
        }

        return ingredientMapper.ingredientListToDtoList(foundIngredients);
    }

    @Override
    public List<RecipeDTO> getAllRecipesInIngredientByIngredientId(String ingredientId) {
        Ingredient ingredient = this.getIngredientById(ingredientId);

        List<Recipe> recipes = ingredient.getRecipes();

        return recipeMapper.recipeListToDTOList(recipes);
    }

    /* db문제로 배제, 현제 식재료 DB를 얻을 수 있을지도 불안정한 상태
    @Override
    public List<Ingredient> findAllByCategory(String category) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*" + category + ".*"));    //name이 포함된 모든 이름에 대해 검색

        List<Ingredient> foundIngredients = template.find(query, Ingredient.class);

        if(foundIngredients.isEmpty()){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient category in Repository, category=" + category));
        }

        return foundIngredients;
    } */

    @Override
    public void updateById(String targetId, IngredientDTO ingredientDTO) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetId));

        Update update = new Update();
        update.set("name", ingredientDTO.getName());
        //update.set("type", ingredient.getType());
        UpdateResult updateResult = template.updateMulti(query, update, Ingredient.class);

        log.info("update id={} to ingredient ={}", targetId,ingredientDTO);

        if (updateResult.getModifiedCount() ==0){   //아무것도 변경되지 않은 경우, 해당 id가 존재하지 않는 것
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient id in Repository id=" + targetId));
        }

    }

    @Override
    public void deleteById(String targetId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetId));

        Ingredient removedIngredient = template.findAndRemove(query, Ingredient.class);
        log.info("delete ingredient ={}", removedIngredient);

        if(removedIngredient ==null){
            throw new NoSuchIngredientException( new NullPointerException("no such ingredient name in Repository, id=" + targetId));
        }

    }

}
