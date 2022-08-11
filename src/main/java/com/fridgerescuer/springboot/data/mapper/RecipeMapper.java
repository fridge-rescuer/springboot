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

    RecipeDTO recipeToDTO(Recipe recipe);
    Recipe DTOtoRecipe(RecipeDTO recipe);
    Recipe responseDTOtoRecipe(RecipeResponseDTO recipe);
    RecipeResponseDTO recipeToResponseDTO(Recipe recipe);
    List<RecipeDTO> recipeListToDtoList(List<Recipe> recipes);
}
