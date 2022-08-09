package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;

import java.util.List;

public interface RecipeService {
    RecipeResponseDTO saveRecipe(RecipeDTO recipeDTO);
    RecipeResponseDTO saveRecipeByMember(String memberId,RecipeDTO recipeDTO);
    List<RecipeResponseDTO> findAllRecipesByContainName(String name);
    List<RecipeResponseDTO> findRecipesByIngredient(IngredientDTO ingredientDTO);
    List<RecipeResponseDTO> findRecipesByMultipleIngredients(List<IngredientDTO> ingredientDTOs);
}
