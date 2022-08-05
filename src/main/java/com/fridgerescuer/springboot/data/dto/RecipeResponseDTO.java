package com.fridgerescuer.springboot.data.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RecipeResponseDTO {
    private String id;
    private String name;
    private String type;
    private String[] ingredientNames;

    public RecipeResponseDTO(String id, String name, String type, String[] ingredientNames) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.ingredientNames = ingredientNames;
    }
}
