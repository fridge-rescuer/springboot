package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.vo.IngredientVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-15T13:16:11+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
public class IngredientMapperImpl implements IngredientMapper {

    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;

    @Override
    public Ingredient DTOtoIngredient(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.recipes( recipeDTOListToRecipeList( ingredientDTO.getRecipeDTOs() ) );
        ingredient.id( ingredientDTO.getId() );
        ingredient.name( ingredientDTO.getName() );

        return ingredient.build();
    }

    @Override
    public IngredientVO DtoToIngredientVO(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        String id = null;
        String name = null;

        id = ingredientDTO.getId();
        name = ingredientDTO.getName();

        IngredientVO ingredientVO = new IngredientVO( id, name );

        return ingredientVO;
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
    public IngredientDTO ingredientToDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO.IngredientDTOBuilder ingredientDTO = IngredientDTO.builder();

        ingredientDTO.recipeDTOs( recipeMapper.recipeListToDTOList( ingredient.getRecipes() ) );
        ingredientDTO.id( ingredient.getId() );
        ingredientDTO.name( ingredient.getName() );

        return ingredientDTO.build();
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

    protected List<Recipe> recipeDTOListToRecipeList(List<RecipeDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Recipe> list1 = new ArrayList<Recipe>( list.size() );
        for ( RecipeDTO recipeDTO : list ) {
            list1.add( recipeMapper.DTOtoRecipe( recipeDTO ) );
        }

        return list1;
    }
}
