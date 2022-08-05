package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;

public interface RecipeService {
    RecipeResponseDTO saveRecipe(RecipeDTO recipeDTO);
}
