package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Member {
    @Id
    private String id;
    private String password;

    private String name;

    private String password;

    @DocumentReference
    private List<Ingredient> ingredients;

    private List<ExpirationData> expirationDataList;

    @DocumentReference
    private List<Recipe> recipes;

    @DocumentReference
    private List<Comment> comments;
}
