package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Recipe;

public interface RecipeDAO {

    Recipe save(Recipe recipe);
}
