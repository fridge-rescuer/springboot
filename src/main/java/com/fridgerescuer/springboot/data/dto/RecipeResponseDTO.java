package com.fridgerescuer.springboot.data.dto;


import com.fridgerescuer.springboot.data.entity.Member;
import lombok.*;

@ToString
@Builder
@Getter
public class RecipeResponseDTO {
    private String id;
    private String name;
    private String type;
    private String[] ingredientNames;

    private MemberDTO producerMember;
}
