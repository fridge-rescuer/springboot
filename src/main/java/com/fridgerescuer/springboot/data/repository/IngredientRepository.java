package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    public Ingredient findByName(String name);

    public List<Ingredient> findAllByName(String name);
    public List<Ingredient> findAllByRepresentationName(String representationName);
    public List<Ingredient> findAllByLargeCategory(String name);
    public List<Ingredient> findAllByMediumCategory(String name);
    public List<Ingredient> findAllBySmallCategory(String name);
    public List<Ingredient> findAllBySubCategory(String name);
}
