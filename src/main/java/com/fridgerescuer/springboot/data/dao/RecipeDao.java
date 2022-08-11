package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Recipe;

import java.util.List;

public interface RecipeDao {

    Recipe save(Recipe recipe);
    Recipe findByName(String name);
    List<Recipe> findAllByContainName(String name);
}
