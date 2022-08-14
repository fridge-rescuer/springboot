package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-14T22:06:05+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
@Component
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public RecipeDTO recipeToDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO.RecipeDTOBuilder recipeDTO = RecipeDTO.builder();

        recipeDTO.producerMember( memberToMemberDTO( recipe.getProducerMember() ) );
        recipeDTO.name( recipe.getName() );
        recipeDTO.type( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipeDTO.ingredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }
        recipeDTO.image( recipe.getImage() );

        return recipeDTO.build();
    }

    @Override
    public Recipe DTOtoRecipe(RecipeDTO recipe) {
        if ( recipe == null ) {
            return null;
        }

        Recipe.RecipeBuilder recipe1 = Recipe.builder();

        recipe1.name( recipe.getName() );
        recipe1.type( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipe1.ingredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }
        recipe1.producerMember( memberDTOToMember( recipe.getProducerMember() ) );
        recipe1.image( recipe.getImage() );

        return recipe1.build();
    }

    @Override
    public Recipe responseDTOtoRecipe(RecipeResponseDTO recipe) {
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
        recipe1.producerMember( memberDTOToMember( recipe.getProducerMember() ) );
        recipe1.image( recipe.getImage() );

        return recipe1.build();
    }

    @Override
    public RecipeResponseDTO recipeToResponseDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeResponseDTO.RecipeResponseDTOBuilder recipeResponseDTO = RecipeResponseDTO.builder();

        recipeResponseDTO.id( recipe.getId() );
        recipeResponseDTO.name( recipe.getName() );
        recipeResponseDTO.type( recipe.getType() );
        String[] ingredientNames = recipe.getIngredientNames();
        if ( ingredientNames != null ) {
            recipeResponseDTO.ingredientNames( Arrays.copyOf( ingredientNames, ingredientNames.length ) );
        }
        recipeResponseDTO.producerMember( memberToMemberDTO( recipe.getProducerMember() ) );
        recipeResponseDTO.image( recipe.getImage() );

        return recipeResponseDTO.build();
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

    @Override
    public List<RecipeResponseDTO> recipeListToResponseDTOList(List<Recipe> recipes) {
        if ( recipes == null ) {
            return null;
        }

        List<RecipeResponseDTO> list = new ArrayList<RecipeResponseDTO>( recipes.size() );
        for ( Recipe recipe : recipes ) {
            list.add( recipeToResponseDTO( recipe ) );
        }

        return list;
    }

    protected MemberDTO memberToMemberDTO(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTO.MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.id( member.getId() );
        memberDTO.name( member.getName() );

        return memberDTO.build();
    }

    protected Member memberDTOToMember(MemberDTO memberDTO) {
        if ( memberDTO == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.id( memberDTO.getId() );
        member.name( memberDTO.getName() );

        return member.build();
    }
}
