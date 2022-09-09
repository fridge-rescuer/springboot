package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class PrivateExpirationData {    //멤버 전용 냉장고 재료, 존재하는 식재료를 참조하지 않음
    private Ingredient ingredient;

    private LocalDate expirationDate;
    private boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true
}
