package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Recipe;

import java.util.List;

public interface RecipeDao {

    Recipe save(Recipe recipe);

    Recipe findById(String id);
    Recipe findByName(String name);
    List<Recipe> findAllByContainName(String name);

    void updateRecipeById(String targetId, Recipe updateData);
}
