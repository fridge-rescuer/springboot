package com.fridgerescuer.springboot.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter @Setter
public class IngredientDTO {
    private String name;
    private String type;

    public IngredientDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
