package com.fridgerescuer.springboot.data.entity;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.*;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.util.List;

@Document(collection = "recipe")
@AllArgsConstructor
@NoArgsConstructor
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

    @DocumentReference
    private Member producerMember;

    private String imageId;

    private String sequence;
    private String category;
    private String cookingType;
    private String ingredientData;
    private String carbohydrate_g;
    private String fat_g;
    private String kcal;
    private String na_mg;
    private String protein_g;

    private String[] manuals;

    private List<String> manualImageIds;

    public Recipe(String name,String sequence, String category, String cookingType, String ingredientData,
                     String carbohydrate_g, String fat_g, String kcal, String na_mg, String protein_g, String[] manuals) {
        this.name = name;
        this.sequence = sequence;
        this.category = category;
        this.cookingType = cookingType;
        this.ingredientData = ingredientData;
        this.carbohydrate_g = carbohydrate_g;
        this.fat_g = fat_g;
        this.kcal = kcal;
        this.na_mg = na_mg;
        this.protein_g = protein_g;
        this.manuals = manuals;
    }
}