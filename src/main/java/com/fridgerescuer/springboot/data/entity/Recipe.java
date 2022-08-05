package com.fridgerescuer.springboot.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.util.Arrays;
import java.util.List;

@Document(collection = "recipe")
@Setter
@Getter
public class Recipe {
    @Id
    @GeneratedValue
    private String id;

    private String name;
    private String type;

    private String[] ingredientNames;

    public Recipe() {}

    public Recipe(String name, String type, String[] ingredientNames) {
        this.name = name;
        this.type = type;
        this.ingredientNames = ingredientNames;
    }
}