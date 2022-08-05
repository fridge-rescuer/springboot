package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T22:10:50+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public RecipeDTO recipeToRecipeDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setName( recipe.getName() );
        recipeDTO.setType( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipeDTO.setIngredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }

        return recipeDTO;
    }

    @Override
    public Recipe recipeDTOToRecipe(RecipeDTO recipe) {
        if ( recipe == null ) {
            return null;
        }

        Recipe recipe1 = new Recipe();

        recipe1.setName( recipe.getName() );
        recipe1.setType( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipe1.setIngredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }

        return recipe1;
    }

    @Override
    public RecipeResponseDTO recipeToRecipeResponseDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();

        recipeResponseDTO.setId( recipe.getId() );
        recipeResponseDTO.setName( recipe.getName() );
        recipeResponseDTO.setType( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipeResponseDTO.setIngredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }

        return recipeResponseDTO;
    }
}
