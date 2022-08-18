package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MapperTest {

    @Test
    void builderWithMapperTest(){
        RecipeDTO recipeDTO = RecipeDTO.builder().name("pizza").type("instance")
                .ingredientNames(new String[]{"ingredient"}).build();
        Recipe recipe = RecipeMapper.INSTANCE.DTOtoRecipe(recipeDTO);

        Assertions.assertThat(recipe.getName()).isEqualTo("pizza");
    }
}