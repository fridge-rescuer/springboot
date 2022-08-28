package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-28T11:42:58+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientDTO ingredientToDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO.IngredientDTOBuilder ingredientDTO = IngredientDTO.builder();

        ingredientDTO.id( ingredient.getId() );
        ingredientDTO.name( ingredient.getName() );

        return ingredientDTO.build();
    }

    @Override
    public Ingredient DTOtoIngredient(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.id( ingredientDTO.getId() );
        ingredient.name( ingredientDTO.getName() );

        return ingredient.build();
    }

    @Override
    public IngredientResponseDTO ingredientToResponseDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientResponseDTO.IngredientResponseDTOBuilder ingredientResponseDTO = IngredientResponseDTO.builder();

        ingredientResponseDTO.id( ingredient.getId() );
        ingredientResponseDTO.name( ingredient.getName() );

        return ingredientResponseDTO.build();
    }

    @Override
    public List<Ingredient> ingredientListToDTOList(List<IngredientDTO> ingredientDTOs) {
        if ( ingredientDTOs == null ) {
            return null;
        }

        List<Ingredient> list = new ArrayList<Ingredient>( ingredientDTOs.size() );
        for ( IngredientDTO ingredientDTO : ingredientDTOs ) {
            list.add( DTOtoIngredient( ingredientDTO ) );
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
            list.add( ingredientToDTO( ingredient ) );
        }

        return list;
    }

    @Override
    public List<IngredientDTO> responseDTOListToDTOList(List<IngredientResponseDTO> ingredientResponseDTOs) {
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
            list.add( ingredientToResponseDTO( ingredient ) );
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

        return ingredientDTO.build();
    }
}
