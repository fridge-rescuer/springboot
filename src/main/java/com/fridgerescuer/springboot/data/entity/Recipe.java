package com.fridgerescuer.springboot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Document(collection = "recipe")
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Recipe {
    @Id
    @GeneratedValue
    private String id;

    private String name;
    private String type;
    private String[] ingredientNames;

    public Recipe() {}

}