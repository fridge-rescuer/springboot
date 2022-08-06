package com.fridgerescuer.springboot.data.dto;


import lombok.*;

@ToString
@Builder
@Getter
public class RecipeResponseDTO {
    private String id;
    private String name;
    private String type;
    private String[] ingredientNames;

}
