package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;

import java.util.List;

public interface RecipeService {
    RecipeResponseDTO saveRecipe(RecipeDTO recipeDTO);
    List<RecipeResponseDTO> findRecipeWithIngredient(IngredientDTO ingredientDTO);
}
