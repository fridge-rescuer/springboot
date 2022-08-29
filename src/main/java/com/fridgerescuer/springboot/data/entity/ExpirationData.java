package com.fridgerescuer.springboot.data.entity;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class ExpirationData {
    String ingredientId;
    LocalDate expirationDate;
    boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true
}
