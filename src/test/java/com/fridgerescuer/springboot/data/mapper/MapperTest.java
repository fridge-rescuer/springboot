package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MapperTest {
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

}