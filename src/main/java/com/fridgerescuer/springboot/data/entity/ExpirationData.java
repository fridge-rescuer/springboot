package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@ToString
public class ExpirationData {

    @DocumentReference
    private Ingredient ingredient;

    private LocalDate expirationDate;
    private boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true
}
