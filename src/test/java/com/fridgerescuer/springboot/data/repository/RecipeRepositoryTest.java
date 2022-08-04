package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@DataMongoTest
class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MongoTemplate template;

    @BeforeEach
    void beforeEach() {  //모두 롤백
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    @DisplayName("연관 관계 적용 확인")
    void relationWithRecipeAndIngredient(){
        //given
        Recipe pastaRecipe = new Recipe("pasta", "파스타");
        Ingredient ingredient1 = new Ingredient("마늘", "채소");
        Ingredient ingredient2 = new Ingredient("스파게티", "면");


        //when
        String id1 = template.insert(ingredient1).getId();
        String id2 = template.insert(ingredient2).getId();

        pastaRecipe.setIngredientNames(id1, id2);
        Recipe insertRecipe = template.insert(pastaRecipe);

        template.update(Ingredient.class)
                .matching(where("id").is(ingredient1.getId()))
                .apply(new Update().push("recipes", insertRecipe))
                .first();

        template.update(Ingredient.class)
                .matching(where("id").is(ingredient2.getId()))
                .apply(new Update().push("recipes", insertRecipe))
                .first();

        Recipe findRecipe = template.findOne(Query.query(where("name").is("pasta")), Recipe.class);
        Ingredient findIngredient = template.findOne(Query.query(where("name").is("마늘")), Ingredient.class);

        //then
        Assertions.assertThat(findIngredient.getRecipes().get(0).getName()).isEqualTo(findRecipe.getName());
    }


    @Test
    @DisplayName("DB가 반환하는 인스턴스가 동일한지 확인")
    void instanceCheck(){
        Ingredient ingredient = new Ingredient("마늘", "채소");

        //when
        /*
        template.insert(ingredient);

        Ingredient ingredient1 = template.findOne(Query.query(where("name").is("마늘")), Ingredient.class);
        Ingredient ingredient2 = template.findOne(Query.query(where("name").is("마늘")), Ingredient.class);
        */

        ingredientRepository.save(ingredient);

        Ingredient ingredient1 = ingredientRepository.findByName("마늘");
        Ingredient ingredient2 = ingredientRepository.findByName("마늘");

        //트랜잭션 적용해 통과될듯
        Assertions.assertThat(ingredient1).isEqualTo(ingredient2);
    }
}