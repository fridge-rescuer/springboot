package com.fridgerescuer.springboot.data.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IngredientResponseDTO {
    private String id;
    private String name;

}
