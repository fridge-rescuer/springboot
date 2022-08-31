package com.fridgerescuer.springboot.data.dto;

import com.fridgerescuer.springboot.data.entity.ExpirationData;
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
    private List<IngredientResponseDTO> ingredientResponseDTOs;
    private List<RecipeResponseDTO> recipeResponseDTOs;

    private List<ExpirationData> expirationDataList;
 }
