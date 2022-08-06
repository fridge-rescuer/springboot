package com.fridgerescuer.springboot.data.dto;

import lombok.*;

@Builder
@Getter
public class RecipeDTO {
    private String name;
    private String type;
    private String[] ingredientNames;

}
