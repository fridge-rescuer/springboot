package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-06T13:36:43+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientDTO ingredientToIngredientDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO.IngredientDTOBuilder ingredientDTO = IngredientDTO.builder();

        ingredientDTO.name( ingredient.getName() );
        ingredientDTO.type( ingredient.getType() );

        return ingredientDTO.build();
    }

    @Override
    public Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.name( ingredientDTO.getName() );
        ingredient.type( ingredientDTO.getType() );

        return ingredient.build();
    }

    @Override
    public IngredientResponseDTO ingredientToIngredientResponseDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientResponseDTO.IngredientResponseDTOBuilder ingredientResponseDTO = IngredientResponseDTO.builder();

        ingredientResponseDTO.id( ingredient.getId() );
        ingredientResponseDTO.name( ingredient.getName() );
        ingredientResponseDTO.type( ingredient.getType() );

        return ingredientResponseDTO.build();
    }
}
