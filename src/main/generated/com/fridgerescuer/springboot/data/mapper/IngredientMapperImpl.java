package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-08T13:44:59+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientDTO ingredientToIngredientDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO.IngredientDTOBuilder ingredientDTO = IngredientDTO.builder();

        ingredientDTO.id( ingredient.getId() );
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

        ingredient.id( ingredientDTO.getId() );
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

    @Override
    public List<Ingredient> ingredientListToIngredientDTOList(List<IngredientDTO> ingredientDTOs) {
        if ( ingredientDTOs == null ) {
            return null;
        }

        List<Ingredient> list = new ArrayList<Ingredient>( ingredientDTOs.size() );
        for ( IngredientDTO ingredientDTO : ingredientDTOs ) {
            list.add( ingredientDTOToIngredient( ingredientDTO ) );
        }

        return list;
    }

    @Override
    public List<IngredientDTO> ingredientListToDtoList(List<Ingredient> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        List<IngredientDTO> list = new ArrayList<IngredientDTO>( ingredients.size() );
        for ( Ingredient ingredient : ingredients ) {
            list.add( ingredientToIngredientDTO( ingredient ) );
        }

        return list;
    }

    @Override
    public List<IngredientDTO> ingredientResponseDTOListToIngredientDTOList(List<IngredientResponseDTO> ingredientResponseDTOs) {
        if ( ingredientResponseDTOs == null ) {
            return null;
        }

        List<IngredientDTO> list = new ArrayList<IngredientDTO>( ingredientResponseDTOs.size() );
        for ( IngredientResponseDTO ingredientResponseDTO : ingredientResponseDTOs ) {
            list.add( ingredientResponseDTOToIngredientDTO( ingredientResponseDTO ) );
        }

        return list;
    }

    @Override
    public List<IngredientResponseDTO> dtoListToResponseDtoList(List<Ingredient> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        List<IngredientResponseDTO> list = new ArrayList<IngredientResponseDTO>( ingredients.size() );
        for ( Ingredient ingredient : ingredients ) {
            list.add( ingredientToIngredientResponseDTO( ingredient ) );
        }

        return list;
    }

    protected IngredientDTO ingredientResponseDTOToIngredientDTO(IngredientResponseDTO ingredientResponseDTO) {
        if ( ingredientResponseDTO == null ) {
            return null;
        }

        IngredientDTO.IngredientDTOBuilder ingredientDTO = IngredientDTO.builder();

        ingredientDTO.id( ingredientResponseDTO.getId() );
        ingredientDTO.name( ingredientResponseDTO.getName() );
        ingredientDTO.type( ingredientResponseDTO.getType() );

        return ingredientDTO.build();
    }
}
