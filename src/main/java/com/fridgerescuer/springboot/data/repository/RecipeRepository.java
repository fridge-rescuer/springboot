package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    public Recipe findByName(String name);
    public List<Recipe> findAllByName(String name);
}
