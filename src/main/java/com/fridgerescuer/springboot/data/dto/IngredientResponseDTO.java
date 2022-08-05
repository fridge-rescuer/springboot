package com.fridgerescuer.springboot.data.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientResponseDTO {
    private String id;
    private String name;
    private String type;

    public IngredientResponseDTO(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
