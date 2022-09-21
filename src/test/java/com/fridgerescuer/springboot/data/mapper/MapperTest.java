package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.entity.*;
import com.fridgerescuer.springboot.security.dto.AuthorityDTO;
import com.fridgerescuer.springboot.security.entity.Authority;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class MapperTest {
    private final IngredientMapper ingredientMapper = IngredientMapper.INSTANCE;
    private final MemberMapper memberMapper = MemberMapper.INSTANCE;
    private final ExpirationDataMapper expirationDataMapper = ExpirationDataMapper.INSTANCE;
    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;

    @Test
    void ingredientWithInnerRecipeMapper(){
        //given
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        recipeDTOList.add(RecipeDTO.builder().name("고구마 전").build());
        recipeDTOList.add(RecipeDTO.builder().name("고구마 죽").build());

        IngredientDTO sweetDTO = IngredientDTO.builder().name("고구마").recipeDTOs(recipeDTOList).build();

        //when
        Ingredient ingredient = ingredientMapper.DTOtoIngredient(sweetDTO);

        //then
        assertThat(ingredient.getRecipes().size()).isEqualTo(2);
        assertThat(ingredient.getRecipes().get(0).getName()).isEqualTo("고구마 전");
    }

    @Test
    void builderWithMapperTest(){
        RecipeDTO recipeDTO = RecipeDTO.builder().name("pizza").type("instance")
                .ingredientNames(new String[]{"ingredient"}).build();
        Recipe recipe = RecipeMapper.INSTANCE.DTOtoRecipe(recipeDTO);

        assertThat(recipe.getName()).isEqualTo("pizza");
    }

    @Test
    void commentListToDTOListMapper(){
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder().body("1").build());
        comments.add(Comment.builder().body("2").build());
        comments.add(Comment.builder().body("3").build());

        List<CommentDTO> responses = CommentMapper.INSTANCE.commentListToDTOList(comments);
        for (int i = 0; i < responses.size() ; i++) {
            assertThat(responses.get(i).getBody()).isEqualTo(comments.get(i).getBody());
        }
    }

    @Test
    void expirationMapper(){
        Ingredient ingredient = Ingredient.builder().name("엿").build();

        ExpirationData expirationData = ExpirationData.builder().ingredient(ingredient).build();
        ExpirationDataDTO expirationDataDTO = ExpirationDataMapper.INSTANCE.dataToDTO(expirationData);

        assertThat(expirationDataDTO.getIngredientDTO().getName()).isEqualTo(ingredient.getName());
    }


    @Test
    void mappingExpirationListToDTOList(){
        Ingredient ingredient = Ingredient.builder().name("엿").build();
        ExpirationData expirationData = ExpirationData.builder().ingredient(ingredient).build();

        List<ExpirationData> expirationDataList = new ArrayList<>();
        expirationDataList.add(expirationData);
        expirationDataList.add(expirationData);
        expirationDataList.add(expirationData);

        List<ExpirationDataDTO> expirationDataDTOList = ExpirationDataMapper.INSTANCE.dataListToDTOList(expirationDataList);
        assertThat(expirationDataDTOList.size()).isEqualTo(3);
        assertThat(expirationDataDTOList.get(0).getIngredientDTO().getName()).isEqualTo(ingredient.getName());
    }

    @Test
    void mappingExpirationDTOListToPrivateList(){
        IngredientDTO ingredient = IngredientDTO.builder().name("엿").build();
        ExpirationDataDTO expirationData = ExpirationDataDTO.builder().ingredientDTO(ingredient).build();

        List<ExpirationDataDTO> expirationDataDTOS = new ArrayList<>();
        expirationDataDTOS.add(expirationData);
        expirationDataDTOS.add(expirationData);
        expirationDataDTOS.add(expirationData);

        List<PrivateExpirationData> privateExpirationDataList = ExpirationDataMapper.INSTANCE.DTOListToPrivateDataList(expirationDataDTOS);
        assertThat(privateExpirationDataList.size()).isEqualTo(3);
        assertThat(privateExpirationDataList.get(0).getIngredient().getName()).isEqualTo(ingredient.getName());
    }

    @Test
    void MemberWithExpirationListToDTOList(){
        Ingredient ingredient = Ingredient.builder().name("엿").build();
        ExpirationData expirationData = ExpirationData.builder().ingredient(ingredient).build();

        List<ExpirationData> expirationDataList = new ArrayList<>();
        expirationDataList.add(expirationData);
        expirationDataList.add(expirationData);
        expirationDataList.add(expirationData);

        Member member = Member.builder().expirationDataList(expirationDataList).build();
        MemberDTO memberDTO = MemberMapper.INSTANCE.memberToDto(member);
        List<ExpirationDataDTO> expirationDataDTOList = memberDTO.getExpirationDataDTOList();

        assertThat(expirationDataDTOList.size()).isEqualTo(3);
        assertThat(expirationDataDTOList.get(2).getIngredientDTO().getName()).isEqualTo(ingredient.getName());
    }

    @Test
    void MemberWithAuthorities(){
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder().authorities(Collections.singleton(authority)).build();

        MemberDTO memberDTO = memberMapper.memberToDto(member);
        Set<AuthorityDTO> authorityDtoSet = memberDTO.getAuthorityDtoSet();

        Iterator<AuthorityDTO> iterator = authorityDtoSet.iterator();
        assertThat(iterator.next().getAuthorityName()).isEqualTo(authority.getAuthorityName());
    }

}