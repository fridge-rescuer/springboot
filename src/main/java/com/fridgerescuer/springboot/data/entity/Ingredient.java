package com.fridgerescuer.springboot.data.entity;

import com.fridgerescuer.springboot.data.entity.component.*;
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
    @Id @GeneratedValue
    private String id;
    private String name;

    private double index;

    private String foodGroup;
    private String foodCode;

    private String representationName;
    private String largeCategory;
    private String mediumCategory;
    private String smallCategory;
    private String subCategory;

    private GeneralComponent generalComponent;
    private Mineral mineral;
    private Vitamin vitamin;
    private AminoAcid aminoAcid;
    private FattyAcid fattyAcid;

    @DocumentReference
    private List<Recipe> recipes;
}