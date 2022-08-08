package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@Getter
public class MemberResponseDto {
    private String id;
    private String name;
    private List<IngredientDTO> ingredientDTOs;

    public void setIngredientDTOs(List<IngredientDTO> ingredientDTOs) {
        this.ingredientDTOs = ingredientDTOs;
    }
}
