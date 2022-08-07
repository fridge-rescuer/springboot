package com.fridgerescuer.springboot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Member {
    @Id @GeneratedValue
    private String id;

    private String name;

    @DocumentReference
    private List<Ingredient> ingredients;
}
