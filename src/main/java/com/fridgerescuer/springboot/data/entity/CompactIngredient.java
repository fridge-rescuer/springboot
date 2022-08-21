package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.util.List;

@Document(collection = "compactIngredient")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class CompactIngredient {
    @Id @GeneratedValue
    private String id;
    private String name;

    @DocumentReference
    private List<Recipe> recipes;
}
