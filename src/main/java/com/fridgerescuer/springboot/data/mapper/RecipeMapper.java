package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeDTO recipeToRecipeDTO(Recipe recipe);
    Recipe recipeDTOToRecipe(RecipeDTO recipe);
    Recipe responseDTOToRecipe(RecipeResponseDTO recipe);
    RecipeResponseDTO recipeToRecipeResponseDTO(Recipe recipe);
    List<RecipeDTO> recipeListToDtoList(List<Recipe> recipes);
}
