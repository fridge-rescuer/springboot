package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Recipe;
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
    void commentListToResponseListMapper(){
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder().body("1").build());
        comments.add(Comment.builder().body("2").build());
        comments.add(Comment.builder().body("3").build());

        List<CommentDTO> responses = CommentMapper.INSTANCE.commentListToDTOList(comments);
        for (int i = 0; i < responses.size() ; i++) {
            assertThat(responses.get(i).getBody()).isEqualTo(comments.get(i).getBody());
        }
    }
}