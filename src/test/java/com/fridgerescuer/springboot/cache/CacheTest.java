package com.fridgerescuer.springboot.cache;

import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.exception.exceptionimpl.RecipeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
public class CacheTest {

    @Autowired
    private RecipeDao recipeDao;

    @Test
    void evictRecipeCache(){
        //given
        RecipeDTO recipe = RecipeDTO.builder().name("김피탕").build();
        String id = recipeDao.save(recipe).getId();

        RecipeDTO firstCache = recipeDao.findById(id);
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

}
