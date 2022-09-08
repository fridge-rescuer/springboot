package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-04T21:43:35+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;

    @Override
    public MemberDTO memberToDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTO.MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.ingredientDTOs( ingredientListToIngredientDTOList( member.getIngredients() ) );
        memberDTO.recipeDTOs( recipeMapper.recipeListToDTOList( member.getRecipes() ) );
        memberDTO.id( member.getId() );
        memberDTO.name( member.getName() );
        memberDTO.password( member.getPassword() );
        List<ExpirationData> list2 = member.getExpirationDataList();
        if ( list2 != null ) {
            memberDTO.expirationDataList( new ArrayList<ExpirationData>( list2 ) );
        }

        return memberDTO.build();
    }

    @Override
    public Member DtoToMember(MemberDTO memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.ingredients( ingredientDTOListToIngredientList( memberDto.getIngredientDTOs() ) );
        member.recipes( recipeDTOListToRecipeList( memberDto.getRecipeDTOs() ) );
        member.id( memberDto.getId() );
        member.password( memberDto.getPassword() );
        member.name( memberDto.getName() );
        List<ExpirationData> list2 = memberDto.getExpirationDataList();
        if ( list2 != null ) {
            member.expirationDataList( new ArrayList<ExpirationData>( list2 ) );
        }

        return member.build();
    }

    @Override
    public MemberResponseDTO memberToResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDTO.MemberResponseDTOBuilder memberResponseDTO = MemberResponseDTO.builder();

        memberResponseDTO.ingredientResponseDTOs( ingredientListToIngredientResponseDTOList( member.getIngredients() ) );
        memberResponseDTO.recipeResponseDTOs( recipeMapper.recipeListToResponseDTOList( member.getRecipes() ) );
        memberResponseDTO.id( member.getId() );
        memberResponseDTO.name( member.getName() );
        memberResponseDTO.password( member.getPassword() );
        List<ExpirationData> list2 = member.getExpirationDataList();
        if ( list2 != null ) {
            memberResponseDTO.expirationDataList( new ArrayList<ExpirationData>( list2 ) );
        }

        return memberResponseDTO.build();
    }

    @Override
    public MemberResponseDTO DtoToMemberResponseDto(MemberDTO memberDTO) {
        if ( memberDTO == null ) {
            return null;
        }

        MemberResponseDTO.MemberResponseDTOBuilder memberResponseDTO = MemberResponseDTO.builder();

        memberResponseDTO.id( memberDTO.getId() );
        memberResponseDTO.name( memberDTO.getName() );
        memberResponseDTO.password( memberDTO.getPassword() );
        List<ExpirationData> list = memberDTO.getExpirationDataList();
        if ( list != null ) {
            memberResponseDTO.expirationDataList( new ArrayList<ExpirationData>( list ) );
        }

        return memberResponseDTO.build();
    }

    protected IngredientDTO ingredientToIngredientDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO.IngredientDTOBuilder ingredientDTO = IngredientDTO.builder();

        ingredientDTO.id( ingredient.getId() );
        ingredientDTO.name( ingredient.getName() );

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

    protected Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.id( ingredientDTO.getId() );
        ingredient.name( ingredientDTO.getName() );

        return ingredient.build();
    }

    protected List<Ingredient> ingredientDTOListToIngredientList(List<IngredientDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Ingredient> list1 = new ArrayList<Ingredient>( list.size() );
        for ( IngredientDTO ingredientDTO : list ) {
            list1.add( ingredientDTOToIngredient( ingredientDTO ) );
        }

        return list1;
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

    protected IngredientResponseDTO ingredientToIngredientResponseDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientResponseDTO.IngredientResponseDTOBuilder ingredientResponseDTO = IngredientResponseDTO.builder();

        ingredientResponseDTO.id( ingredient.getId() );
        ingredientResponseDTO.name( ingredient.getName() );

        return ingredientResponseDTO.build();
    }

    protected List<IngredientResponseDTO> ingredientListToIngredientResponseDTOList(List<Ingredient> list) {
        if ( list == null ) {
            return null;
        }

        List<IngredientResponseDTO> list1 = new ArrayList<IngredientResponseDTO>( list.size() );
        for ( Ingredient ingredient : list ) {
            list1.add( ingredientToIngredientResponseDTO( ingredient ) );
        }

        return list1;
    }
}
