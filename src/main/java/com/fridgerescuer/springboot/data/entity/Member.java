package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.util.List;

@Document(collection = "member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Member {
    @Id @GeneratedValue
    private String id;

    private String name;

    @DocumentReference
    private List<Ingredient> ingredients;

    @DocumentReference
    private List<Recipe> recipes;

    @DocumentReference
    private List<Comment> comments;
}
