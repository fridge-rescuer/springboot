package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.vo.IngredientVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = {RecipeMapper.class})
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    @Mapping(source = "recipeDTOs", target = "recipes")
    Ingredient DTOtoIngredient(IngredientDTO ingredientDTO);

    IngredientVO DtoToIngredientVO(IngredientDTO ingredientDTO);

    List<Ingredient> ingredientListToDTOList(List<IngredientDTO> ingredientDTOs);

    @Mapping(source = "recipes", target = "recipeDTOs")
    IngredientDTO ingredientToDTO(Ingredient ingredient);
    List<IngredientDTO> ingredientListToDtoList(List<Ingredient> ingredients);
}