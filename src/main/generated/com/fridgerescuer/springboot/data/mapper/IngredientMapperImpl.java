package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T22:10:50+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientDTO ingredientToIngredientDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO ingredientDTO = new IngredientDTO();

        ingredientDTO.setName( ingredient.getName() );
        ingredientDTO.setType( ingredient.getType() );

        return ingredientDTO;
    }

    @Override
    public Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setName( ingredientDTO.getName() );
        ingredient.setType( ingredientDTO.getType() );

        return ingredient;
    }
}
