package com.fridgerescuer.springboot.data.dto;

import com.fridgerescuer.springboot.data.entity.Member;
import lombok.*;
import org.bson.types.Binary;

@Builder
@Getter
@ToString
public class RecipeDTO {
    private String name;
    private String type;
    private String[] ingredientNames;

    private MemberDTO producerMember;
    private Binary image;
}
