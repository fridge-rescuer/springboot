package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@Getter
@EqualsAndHashCode
public class MemberResponseDTO {
    private String id;
    private String name;
    private String password;
    private List<IngredientResponseDTO> ingredientResponseDTOs;
    private List<RecipeResponseDTO> recipeResponseDTOs;

}