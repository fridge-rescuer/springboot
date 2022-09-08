package com.fridgerescuer.springboot.service;


import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;

import java.util.Set;

public interface IngredientService {
    void saveIngredient(IngredientDTO ingredientDTO);
    IngredientResponseDTO findIngredientByName(String name);
    public void saveCustomIngredient(IngredientDTO ingredientDTO)
    Set<IngredientResponseDTO> loadAllIngredients();
    IngredientResponseDTO findIngredientById(String id);
}
