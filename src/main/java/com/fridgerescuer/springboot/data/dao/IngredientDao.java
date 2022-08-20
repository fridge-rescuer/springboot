package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Ingredient;

import java.util.List;

public interface IngredientDao {

    Ingredient save(Ingredient ingredient);

    Ingredient find(Ingredient ingredient);
    Ingredient findByName(String name);
    Ingredient findById(String id);
    List<Ingredient> findAllByCategory(String category);

    void update(String targetId, Ingredient ingredient);
    void delete(String targetId);
}
