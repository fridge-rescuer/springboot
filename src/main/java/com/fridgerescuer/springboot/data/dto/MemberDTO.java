package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Builder
@Getter
@ToString
public class MemberDTO {
    private String id;
    private String name;
    private List<IngredientDTO> ingredientDTOs;
    private List<RecipeDTO> recipeDTOs;  //이름 바꾸면 오류터짐
}
