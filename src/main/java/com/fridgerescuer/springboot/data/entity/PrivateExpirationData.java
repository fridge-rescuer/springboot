package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import java.time.LocalDate;

@Document(collection = "privateExpirationData")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class PrivateExpirationData {    //멤버 전용 냉장고 재료, 존재하는 식재료를 참조하지 않음
    @Id @GeneratedValue
    private String id;

    private Ingredient ingredient;

    private LocalDate expirationDate;
    private boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true

    public boolean getIsNoExpiration(){
        return isNoExpiration;
    }
}
