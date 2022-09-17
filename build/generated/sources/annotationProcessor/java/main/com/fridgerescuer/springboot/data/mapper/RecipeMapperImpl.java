package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-15T13:16:11+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public Recipe DTOtoRecipe(RecipeDTO recipe) {
        if ( recipe == null ) {
            return null;
        }

        Recipe.RecipeBuilder recipe1 = Recipe.builder();

        recipe1.id( recipe.getId() );
        recipe1.name( recipe.getName() );
        recipe1.type( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipe1.ingredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }
        recipe1.producerMemberId( recipe.getProducerMemberId() );
        recipe1.ratingAvg( recipe.getRatingAvg() );
        recipe1.ratingTotalSum( recipe.getRatingTotalSum() );

        return recipe1.build();
    }

    @Override
    public RecipeDTO recipeToDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO.RecipeDTOBuilder recipeDTO = RecipeDTO.builder();

        recipeDTO.id( recipe.getId() );
        recipeDTO.name( recipe.getName() );
        recipeDTO.type( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipeDTO.ingredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }
        recipeDTO.producerMemberId( recipe.getProducerMemberId() );
        recipeDTO.ratingAvg( recipe.getRatingAvg() );
        recipeDTO.ratingTotalSum( recipe.getRatingTotalSum() );

        return recipeDTO.build();
    }

    @Override
    public List<RecipeDTO> recipeListToDTOList(List<Recipe> recipes) {
        if ( recipes == null ) {
            return null;
        }

        List<RecipeDTO> list = new ArrayList<RecipeDTO>( recipes.size() );
        for ( Recipe recipe : recipes ) {
            list.add( recipeToDTO( recipe ) );
        }

        return list;
    }
}
