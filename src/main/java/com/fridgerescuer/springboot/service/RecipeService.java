package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeService {
    RecipeResponseDTO saveRecipe(RecipeDTO recipeDTO);
    RecipeResponseDTO saveRecipeByMember(String memberId,RecipeDTO recipeDTO);

    RecipeResponseDTO findById(String id);
    List<RecipeResponseDTO> findAllRecipesByContainName(String name);
    List<RecipeResponseDTO> findRecipesByIngredient(IngredientDTO ingredientDTO);
    List<RecipeResponseDTO> findRecipesByMultipleIngredients(List<IngredientDTO> ingredientDTOs);

    void updateRecipeById(String recipeId,RecipeDTO updateRecipeDTO);
    void deleteRecipeById(String recipeId);

    void addRecipeImage(String targetId, MultipartFile image) throws IOException;
}
