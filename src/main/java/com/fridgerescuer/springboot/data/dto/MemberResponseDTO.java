package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@Getter
public class MemberResponseDTO {
    private String id;
    private String name;
    private List<IngredientDTO> ingredientDTOs;
    private List<RecipeDTO> recipeDTOs;

 }