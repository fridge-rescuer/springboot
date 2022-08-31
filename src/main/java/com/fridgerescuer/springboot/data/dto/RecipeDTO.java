package com.fridgerescuer.springboot.data.dto;

import com.fridgerescuer.springboot.data.entity.Member;
import lombok.*;
import org.bson.types.Binary;

import java.util.List;

@Builder
@Getter
@ToString
public class RecipeDTO {
    private String id;
    private String name;
    private String type;
    private String[] ingredientNames;

    private String producerMemberId;

    private double ratingAvg;
    private double ratingTotalSum;
}
