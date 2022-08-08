package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);
    Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO);
    IngredientResponseDTO ingredientToIngredientResponseDTO(Ingredient ingredient);
    List<Ingredient> ingredientListToIngredientDTOList(List<IngredientDTO> ingredientDTOs);
    List<IngredientDTO> ingredientListToDtoList(List<Ingredient> ingredients);
    List<IngredientDTO> ingredientResponseDTOListToIngredientDTOList(List<IngredientResponseDTO> ingredientResponseDTOs);
    List<IngredientResponseDTO> dtoListToResponseDtoList(List<Ingredient> ingredients);
}
