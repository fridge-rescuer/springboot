package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(componentModel = "spring")
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe DTOtoRecipe(RecipeDTO recipe);
    //Recipe responseDTOtoRecipe(RecipeResponseDTO recipe);

    RecipeDTO recipeToDTO(Recipe recipe);

    //RecipeResponseDTO recipeToResponseDTO(Recipe recipe);

    List<RecipeDTO> recipeListToDTOList(List<Recipe> recipes);
    //List<RecipeResponseDTO> recipeListToResponseDTOList(List<Recipe> recipes);
}