package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    @Mapping(source = "producerMember", target = "producerMember")
    RecipeDTO recipeToDTO(Recipe recipe);
    Recipe DTOtoRecipe(RecipeDTO recipe);
    Recipe responseDTOtoRecipe(RecipeResponseDTO recipe);
    RecipeResponseDTO recipeToResponseDTO(Recipe recipe);

    List<RecipeDTO> recipeListToDTOList(List<Recipe> recipes);
    List<RecipeResponseDTO> recipeListToResponseDTOList(List<Recipe> recipes);
}
