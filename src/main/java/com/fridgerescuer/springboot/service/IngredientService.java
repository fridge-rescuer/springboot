package com.fridgerescuer.springboot.service;


import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;

public interface IngredientService {
    IngredientResponseDTO saveIngredient(IngredientDTO ingredientDTO);
    IngredientResponseDTO findIngredientByName(String name);
    IngredientResponseDTO findIngredientById(String id);
    void updateIngredient(String id, IngredientDTO ingredientDTO);
    void deleteIngredient(String id);
}
