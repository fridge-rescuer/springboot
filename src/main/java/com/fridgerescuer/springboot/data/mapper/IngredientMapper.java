package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(componentModel = "spring", uses = {})
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO ingredientToDTO(Ingredient ingredient);
    Ingredient DTOtoIngredient(IngredientDTO ingredientDTO);
    //IngredientResponseDTO ingredientToResponseDTO(Ingredient ingredient);
    List<Ingredient> ingredientListToDTOList(List<IngredientDTO> ingredientDTOs);
    List<IngredientDTO> ingredientListToDtoList(List<Ingredient> ingredients);
    //List<IngredientDTO> responseDTOListToDTOList(List<IngredientResponseDTO> ingredientResponseDTOs);
    //List<IngredientResponseDTO> dtoListToResponseDtoList(List<Ingredient> ingredients);
}