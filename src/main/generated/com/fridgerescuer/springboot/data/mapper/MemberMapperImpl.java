package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-14T21:12:21+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDTO memberToDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTO.MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.id( member.getId() );
        memberDTO.name( member.getName() );

        return memberDTO.build();
    }

    @Override
    public Member DtoToMember(MemberDTO memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.id( memberDto.getId() );
        member.name( memberDto.getName() );

        return member.build();
    }

    @Override
    public MemberResponseDTO memberToResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDTO.MemberResponseDTOBuilder memberResponseDTO = MemberResponseDTO.builder();

        memberResponseDTO.ingredientDTOs( ingredientListToIngredientDTOList( member.getIngredients() ) );
        memberResponseDTO.recipeDTOs( recipeListToRecipeDTOList( member.getRecipes() ) );
        memberResponseDTO.id( member.getId() );
        memberResponseDTO.name( member.getName() );

        return memberResponseDTO.build();
    }

    protected IngredientDTO ingredientToIngredientDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO.IngredientDTOBuilder ingredientDTO = IngredientDTO.builder();

        ingredientDTO.id( ingredient.getId() );
        ingredientDTO.name( ingredient.getName() );
        ingredientDTO.type( ingredient.getType() );

        return ingredientDTO.build();
    }

    protected List<IngredientDTO> ingredientListToIngredientDTOList(List<Ingredient> list) {
        if ( list == null ) {
            return null;
        }

        List<IngredientDTO> list1 = new ArrayList<IngredientDTO>( list.size() );
        for ( Ingredient ingredient : list ) {
            list1.add( ingredientToIngredientDTO( ingredient ) );
        }

        return list1;
    }

    protected RecipeDTO recipeToRecipeDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO.RecipeDTOBuilder recipeDTO = RecipeDTO.builder();

        recipeDTO.name( recipe.getName() );
        recipeDTO.type( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipeDTO.ingredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }
        recipeDTO.producerMember( memberToDto( recipe.getProducerMember() ) );
        recipeDTO.image( recipe.getImage() );

        return recipeDTO.build();
    }

    protected List<RecipeDTO> recipeListToRecipeDTOList(List<Recipe> list) {
        if ( list == null ) {
            return null;
        }

        List<RecipeDTO> list1 = new ArrayList<RecipeDTO>( list.size() );
        for ( Recipe recipe : list ) {
            list1.add( recipeToRecipeDTO( recipe ) );
        }

        return list1;
    }
}
