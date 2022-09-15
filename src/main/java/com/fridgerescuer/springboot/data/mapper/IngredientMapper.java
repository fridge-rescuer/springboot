package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.vo.IngredientVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(componentModel = "spring", uses = {})
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO ingredientToDTO(Ingredient ingredient);
    Ingredient DTOtoIngredient(IngredientDTO ingredientDTO);

    IngredientVO DtoToIngredientVO(IngredientDTO ingredientDTO);

//    public Set<IngredientResponseDTO> loadAllIngredients();

    List<Ingredient> ingredientListToDTOList(List<IngredientDTO> ingredientDTOs);
    List<IngredientDTO> ingredientListToDtoList(List<Ingredient> ingredients);
}