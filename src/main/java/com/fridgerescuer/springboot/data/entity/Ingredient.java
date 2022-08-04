package com.fridgerescuer.springboot.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.util.List;

@Document(collection = "ingredient")
@Setter
@Getter
public class Ingredient {
    @Id
    @GeneratedValue
    private String id;

    private String name;
    private String type;

    @DocumentReference
    private List<Recipe> recipes;

    public Ingredient() {
    }

    public Ingredient(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
