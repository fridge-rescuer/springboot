package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.CommentForm;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.service.GlobalService;
import com.fridgerescuer.springboot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private final RecipeService recipeService;
//    @Autowired
//    private final GlobalService globalService;

    @PostMapping("/create")
    public RecipeDTO createRecipe(@RequestBody RecipeDTO recipeDTO) {
        //토큰 꺼내는 작업
        String memberToken = null;

        return recipeService.createRecipe(recipeDTO, memberToken);
    }

    @GetMapping("/{recipeId}")
    public RecipeDTO getRecipeDetail(@PathVariable("recipeId") String recipeId) {
        return recipeService.getRecipeDetail(recipeId);
    }

    @PostMapping("/{recipeId}/update")
    public RecipeDTO updateRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.updateRecipe(recipeDTO);
    }

    @DeleteMapping("/{recipeId}/delete")
    public void deleteRecipe(@RequestBody RecipeDTO recipeDTO) {
        recipeService.deleteRecipe(recipeDTO);
    }

    @PostMapping("/{recipeId}/comments/create")
    public void createComment(@PathVariable("recipeId") String recipeId, @ModelAttribute CommentForm commentForm) {
        //memberToken 전달
        String memberToken = null;
        RecipeDTO recipeDTO = recipeService.getRecipeDetail(recipeId);
        CommentDTO commentDTO = CommentDTO.builder().body(commentForm.getBody()).rating(commentForm.getRating()).date(LocalDateTime.now()).recipeId(recipeId).build();
        recipeService.createRecipeComment(recipeDTO, memberToken, commentDTO, commentForm.getImages());
    }

    //추후 사진 변경도 추가해야함
    @PostMapping("/{recipeId}/comments/{commentId}/update")
    public void updateComment(@PathVariable("recipeId") String recipeId, @PathVariable("commentId") String commentId, CommentForm tryUpdateComment) {
        CommentDTO commentDTO = recipeService.getRecipeComment(commentId);
        commentDTO.setBody(tryUpdateComment.getBody());
        commentDTO.setRating(tryUpdateComment.getRating());

        //추후 token 추출
        String memberToken = null;
        recipeService.updateRecipeComment(commentDTO, memberToken);
    }

    @DeleteMapping("/{recipeId}/comments/{commentId}/delete")
    public void deleteComment(@PathVariable("recipeId") String recipeId, @PathVariable("commentId") String commentId) {
        //추후 token 추출
        String memberToken = null;

        recipeService.deleteRecipeComment(commentId, memberToken);

    }

}