package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.util.List;

@Document(collection = "ingredient")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Ingredient {
    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String dataCode;
    private String dataTypeName;
    private String representationName;
    private String originTypeName;
    private String largeCategory;
    private String mediumCategory;
    private String smallCategory;
    private String subCategory;

    @DocumentReference
    private List<Recipe> recipes;

    private Component component;

    public Ingredient(String name, String dataCode, String dataTypeName, String representationName, String originTypeName, String largeCategory, String mediumCategory, String smallCategory, String subCategory
    ,Component component) {
        this.name = name;
        this.dataCode = dataCode;
        this.dataTypeName = dataTypeName;
        this.representationName = representationName;
        this.originTypeName = originTypeName;
        this.largeCategory = largeCategory;
        this.mediumCategory = mediumCategory;
        this.smallCategory = smallCategory;
        this.subCategory = subCategory;
        this.component =component;
    }
}