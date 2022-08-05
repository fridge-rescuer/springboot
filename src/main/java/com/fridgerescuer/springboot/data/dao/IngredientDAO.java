package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Ingredient;

public interface IngredientDAO {

    Ingredient save(Ingredient ingredient);

    Ingredient find(Ingredient ingredient);
}
