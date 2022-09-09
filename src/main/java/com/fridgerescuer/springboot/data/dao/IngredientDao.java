package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;

import java.util.List;

public interface IngredientDao {

    IngredientDTO save(IngredientDTO ingredientDTO);

    IngredientDTO findByName(String name);
    IngredientDTO findById(String id);
    List<IngredientDTO> findAllByContainName(String containName);
    List<RecipeDTO> getAllRecipesInIngredientByIngredientId(String ingredientId);

    void updateById(String targetId, IngredientDTO ingredientDTO);
    void deleteById(String targetId);
}
