package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.*;


@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private RecipeService recipeService;

    //given
    //when
    //then

    @Test
    void saveComment(){
        CommentDTO commentDTO = CommentDTO.builder().rating(4.5).body("정말 맛있어요!! ㅋㅋ큐ㅋ").build();
        RecipeDTO recipeDTO = RecipeDTO.builder().name("중력").build();
        MemberDTO memberDTO = MemberDTO.builder().name("브레드 피트").build();

        MemberResponseDTO memberResponseDTO = memberService.saveMember(memberDTO);
        RecipeResponseDTO recipeResponseDTO = recipeService.saveRecipeByMember(memberResponseDTO.getId(), recipeDTO);


        CommentResponseDTO commentResponseDTO = commentService.saveComment(memberResponseDTO.getId(), recipeResponseDTO.getId(), commentDTO);

        System.out.println(commentResponseDTO.toString());

        assertThat(commentResponseDTO.getBody()).isEqualTo(commentDTO.getBody());
        assertThat(commentResponseDTO.getRecipeId()).isEqualTo(recipeResponseDTO.getId());
    }

    @Test
    void findCommentByMemberAndRecipe(){
        //given
        CommentDTO commentDTO = CommentDTO.builder().rating(4.5).body("맛남").build();
        RecipeDTO recipeDTO = RecipeDTO.builder().name("마라탕").build();
        MemberDTO memberDTO = MemberDTO.builder().name("브레드 피트").build();

        //when
        MemberResponseDTO memberResponseDTO = memberService.saveMember(memberDTO);
        RecipeResponseDTO recipeResponseDTO = recipeService.saveRecipeByMember(memberResponseDTO.getId(), recipeDTO);

        //then


    }

    @Test
    void updateCommentById(){
        //given
        CommentDTO commentDTO = CommentDTO.builder().rating(4.5).body("기대되는 맛~").build();
        RecipeDTO recipeDTO = RecipeDTO.builder().name("마라탕").build();
        MemberDTO memberDTO = MemberDTO.builder().name("브레드 피트").build();

        //when
        MemberResponseDTO memberResponseDTO = memberService.saveMember(memberDTO);
        RecipeResponseDTO recipeResponseDTO = recipeService.saveRecipeByMember(memberResponseDTO.getId(), recipeDTO);

        CommentResponseDTO commentResponseDTO = commentService.saveComment(memberResponseDTO.getId(), recipeResponseDTO.getId(), commentDTO);
        CommentDTO updateCommentDTO = CommentDTO.builder().rating(2.5).body("별로임").build();

        commentService.updateCommentById(commentResponseDTO.getId(), updateCommentDTO); //업데이트 진행

        //then
        assertThat(commentService.findCommentById(commentResponseDTO.getId()).getBody()).isEqualTo(updateCommentDTO.getBody());
        assertThat(recipeService.findById(recipeResponseDTO.getId()).getRatingAvg()).isEqualTo(2.5);
    }
}