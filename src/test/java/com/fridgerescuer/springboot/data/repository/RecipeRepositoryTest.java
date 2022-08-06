package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").type("채소").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").type("식용유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("고추").type("채소").build();

        RecipeDTO recipe = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘","올리브유","고추"}).build();

        //when
        ingredientService.saveIngredient(ingredient1);
        ingredientService.saveIngredient(ingredient2);
        ingredientService.saveIngredient(ingredient3);

        recipeService.saveRecipe(recipe);
        //then
        assertThat(ingredientRepository.findByName("고추").getRecipes().get(0).getName()).isEqualTo(recipe.getName());

    }

    @Test
    @DisplayName("재료로 레시피 검색하기")
    void findRecipesByIngredient(){
        //given
        IngredientDTO ingredient = IngredientDTO.builder().name("마늘").type("채소").build();
        RecipeDTO recipe1 = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘"}).build();
        RecipeDTO recipe2 = RecipeDTO.builder().name("마늘 장아찌").type("발효 식품").ingredientNames(new String[]{"마늘"}).build();

        //when
        ingredientService.saveIngredient(ingredient);
        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);

        List<RecipeResponseDTO> findRecipes = recipeService.findRecipeWithIngredient(ingredient);

        //then
        for (RecipeResponseDTO recipeResponseDTO: findRecipes) {
            System.out.println(recipeResponseDTO.toString());
        }
        assertThat(findRecipes.size()).isEqualTo(2);
    }

}