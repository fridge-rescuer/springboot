package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;

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
}