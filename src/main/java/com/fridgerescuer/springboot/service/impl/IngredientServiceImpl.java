package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDAO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private final IngredientDAO ingredientDAO;  //언제든지 구현체를 대체가능하게 변경

    @Override
    public IngredientResponseDTO saveIngredient(IngredientDTO ingredientDTO){
        Ingredient ingredient = new Ingredient(ingredientDTO.getName(), ingredientDTO.getType());

        Ingredient insertedIngredient = ingredientDAO.save(ingredient);

        return new IngredientResponseDTO(insertedIngredient.getId(), insertedIngredient.getName(), insertedIngredient.getType());
    }
}
