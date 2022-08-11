package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class MemberDTO {
    private String name;
    private List<IngredientDTO> ingredientDTOs;
    private List<RecipeDTO> ingredientDTO;
}
