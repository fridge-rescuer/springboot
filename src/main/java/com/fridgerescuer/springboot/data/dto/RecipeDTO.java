package com.fridgerescuer.springboot.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class RecipeDTO {
    private String name;
    private String type;
    private String[] ingredientNames;

    public RecipeDTO(String name, String type, String[] ingredientNames) {
        this.name = name;
        this.type = type;
        this.ingredientNames = ingredientNames;
    }
}
