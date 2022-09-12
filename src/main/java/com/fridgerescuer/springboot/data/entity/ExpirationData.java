package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.time.LocalDate;

@Document(collection = "expirationData")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class ExpirationData {
    @Id @GeneratedValue
    private String id;

    @DocumentReference
    private Ingredient ingredient;

    private LocalDate expirationDate;
    private boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true

    public boolean getIsNoExpiration(){
        return isNoExpiration;
    }
}
