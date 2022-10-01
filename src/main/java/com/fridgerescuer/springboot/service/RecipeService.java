package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import org.springframework.web.multipart.MultipartFile;

public interface RecipeService {
    RecipeDTO updateRecipe(RecipeDTO updatedRecipe);

    void deleteRecipe(RecipeDTO deleteRecipe);

    RecipeDTO createRecipe(RecipeDTO trySaveRecipe, String memberToken);

    RecipeDTO getRecipeDetail(String recipeId);

    void createRecipeComment(RecipeDTO recipeDTO, String memberToken, CommentDTO trySaveComment, MultipartFile image);

    void updateRecipeComment(CommentDTO commentDTO, String memberToken);

    void deleteRecipeComment(String commentId, String memberToken);

    CommentDTO getRecipeComment(String commentId);
}
