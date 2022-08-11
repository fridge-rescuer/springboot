package com.fridgerescuer.springboot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.util.List;

@Document(collection = "ingredient")
@AllArgsConstructor
@Builder
@Getter
@ToString
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

}
