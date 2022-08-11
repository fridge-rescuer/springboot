package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Ingredient;

public interface IngredientDao {

    Ingredient save(Ingredient ingredient);

    Ingredient find(Ingredient ingredient);

    Ingredient findByName(String name);
}
