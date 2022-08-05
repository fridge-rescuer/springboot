package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.RecipeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;


    @BeforeEach
    void beforeEach() {  //모두 롤백
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    //given
    //when
    //then

    @Test
    @DisplayName("레시피 저장, 재료와 연관")
    void saveRecipeWithIngredient(){
        //given
        IngredientDTO ingredient1 = new IngredientDTO("마늘", "채소");
        IngredientDTO ingredient2 = new IngredientDTO("올리브유", "식용유");
        IngredientDTO ingredient3 = new IngredientDTO("고추", "채소");

        RecipeDTO recipe = new RecipeDTO("알리오 올리오", "파스타", new String[]{"마늘","올리브유","고추"});

        //when
        ingredientService.saveIngredient(ingredient1);
        ingredientService.saveIngredient(ingredient2);
        ingredientService.saveIngredient(ingredient3);

        recipeService.saveRecipe(recipe);
        //then
        Assertions.assertThat(ingredientRepository.findByName("고추").getRecipes().get(0).getName()).isEqualTo(recipe.getName());

    }

}