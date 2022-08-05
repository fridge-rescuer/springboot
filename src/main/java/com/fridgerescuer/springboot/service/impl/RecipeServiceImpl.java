package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.RecipeDAO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private final RecipeDAO recipeDAO;

    @Override
    public RecipeResponseDTO saveRecipe(RecipeDTO recipeDTO) {
        Recipe savedRecipe = recipeDAO.save(new Recipe(recipeDTO.getName(), recipeDTO.getType(), recipeDTO.getIngredientNames()));

        return new RecipeResponseDTO(savedRecipe.getId(), savedRecipe.getName(), savedRecipe.getType(), savedRecipe.getIngredientNames());
    }
}
