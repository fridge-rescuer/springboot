package com.fridgerescuer.springboot.data.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class ExpirationDataDTO {
    private String id;

    private IngredientDTO ingredientDTO;

    private LocalDate expirationDate;
    private boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true

    public boolean getIsNoExpiration(){
        return isNoExpiration;
    }
}
