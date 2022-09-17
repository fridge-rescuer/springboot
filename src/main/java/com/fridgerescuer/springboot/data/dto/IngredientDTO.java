package com.fridgerescuer.springboot.data.dto;

import com.fridgerescuer.springboot.data.entity.Recipe;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    private String id;
    private String name;

    private List<RecipeDTO> recipeDTOs;
}
