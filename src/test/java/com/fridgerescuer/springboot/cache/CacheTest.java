package com.fridgerescuer.springboot.cache;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.exception.exceptionimpl.IngredientException;
import com.fridgerescuer.springboot.exception.exceptionimpl.RecipeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
public class CacheTest {

    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private IngredientDao ingredientDao;

    @Test
    void evictRecipeCache(){
        //given
        RecipeDTO recipe = RecipeDTO.builder().name("김피탕").build();
        String id = recipeDao.save(recipe).getId();

        RecipeDTO firstCache = recipeDao.findById(id);
        recipeDao.findById(id);
        recipeDao.findById(id);
        recipeDao.findById(id);
        recipeDao.findById(id);
        RecipeDTO updateDTO = RecipeDTO.builder().name("짬뽕").build();

        //when
        recipeDao.updateRecipeById(id,updateDTO);
        RecipeDTO secondCache = recipeDao.findById(id);

        //then
        Assertions.assertThat(secondCache.getName()).isEqualTo(updateDTO.getName());

        //when2
        recipeDao.deleteById(id);

        //then2
        Assertions.assertThatThrownBy(() -> recipeDao.findById(id)).isInstanceOf(RecipeException.class);
    }

    @Test
    void evictIngredientCache(){
        //given
        IngredientDTO ingredient = IngredientDTO.builder().name("김치").build();
        String id = ingredientDao.save(ingredient).getId();

        Set<String> ingredientIds = new HashSet<>();
        ingredientIds.add(id);

        RecipeDTO recipe = RecipeDTO.builder().name("김피탕").ingredientIds(ingredientIds).build();
        RecipeDTO savedRecipeDto = recipeDao.save(recipe);

        IngredientDTO firstCache = ingredientDao.findById(id);

        recipeDao.updateRecipeById(savedRecipeDto.getId(),RecipeDTO.builder().name("짬뽕").ingredientIds(ingredientIds).build());
        IngredientDTO recipeUpdatedCache = ingredientDao.findById(id);
        Assertions.assertThat(recipeUpdatedCache.getRecipeDTOs().get(0).getName()).isEqualTo("짬뽕");


        ingredientDao.findById(id);
        IngredientDTO updateDTO = IngredientDTO.builder().name("피자").build();

        //when
        ingredientDao.updateById(id, updateDTO);
        IngredientDTO secondCache = ingredientDao.findById(id);

        //then
        Assertions.assertThat(secondCache.getName()).isEqualTo(updateDTO.getName());

        //when2
        ingredientDao.deleteById(id);

        //then2
        Assertions.assertThatThrownBy(() -> ingredientDao.findById(id)).isInstanceOf(IngredientException.class);
    }

}
