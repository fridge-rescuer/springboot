package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDAO;
import com.fridgerescuer.springboot.data.dao.RecipeDAO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.mapper.RecipeMapper;
import com.fridgerescuer.springboot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    @Autowired private final RecipeDAO recipeDAO;
    @Autowired private final IngredientDAO ingredientDAO;

    @Override
    public RecipeResponseDTO saveRecipe(RecipeDTO recipeDTO) {
        Recipe savedRecipe = recipeDAO.save(RecipeMapper.INSTANCE.recipeDTOToRecipe(recipeDTO));

        return RecipeMapper.INSTANCE.recipeToRecipeResponseDTO(savedRecipe);
    }

    @Override
    public List<RecipeResponseDTO> findRecipeWithIngredient(IngredientDTO ingredientDTO) {
        Ingredient findIngredient = ingredientDAO.find(Ingredient.builder()
                .name(ingredientDTO.getName())
                .type(ingredientDTO.getType()).build());

        List<RecipeResponseDTO> results = new ArrayList<>();

        for(Recipe recipe :findIngredient.getRecipes()){
            results.add(RecipeMapper.INSTANCE.recipeToRecipeResponseDTO(recipe));
        }

        return results;
    }

}
