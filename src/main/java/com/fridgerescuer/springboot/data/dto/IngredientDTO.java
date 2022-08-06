package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class IngredientDTO {
    private String name;
    private String type;

}
