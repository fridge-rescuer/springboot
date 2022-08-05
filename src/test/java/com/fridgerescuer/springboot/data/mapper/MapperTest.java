package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {
    @Test
    void mapperTest(){
        RecipeDTO recipeDTO = new RecipeDTO("pizza", "type", new String[]{"ingredient"});
        Recipe recipe = RecipeMapper.INSTANCE.recipeDTOToRecipe(recipeDTO);

        Assertions.assertThat(recipe.getName()).isEqualTo("pizza");
    }
}