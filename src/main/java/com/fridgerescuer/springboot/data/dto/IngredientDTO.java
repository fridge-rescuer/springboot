package com.fridgerescuer.springboot.data.dto;

import com.fridgerescuer.springboot.data.entity.Recipe;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class IngredientDTO {
    private String id;
    private String name;

    private List<RecipeDTO> recipeDTOs;
}
