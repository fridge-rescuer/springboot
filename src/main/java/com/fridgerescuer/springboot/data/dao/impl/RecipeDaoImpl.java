package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component  //빈으로 등록되어 다른 클래스가 인터페이스로 의존성 주입 받을 때 자동 등록됨
@RequiredArgsConstructor
public class RecipeDaoImpl implements RecipeDao {

    @Autowired
    private final RecipeRepository repository;

    @Autowired
    private final MongoTemplate template;

    @Override
    public Recipe save(Recipe recipe) {
        Recipe savedRecipe = repository.save(recipe);

        String[] ingredientNames = recipe.getIngredientNames(); //재료에 해당 레시피 연관시킴
        for (String name:ingredientNames) {
            template.update(Ingredient.class)
                    .matching(where("name").is(name))
                    .apply(new Update().push("recipes", savedRecipe))
                    .first();
        }

        return savedRecipe;
    }

    @Override
    public Recipe findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Recipe> findAllByContainName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*" + name + ".*"));    //name이 포함된 모든 이름에 대해 검색

        return template.find(query, Recipe.class);
    }
}
