package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CommentDTO {
    private String id;

    private double rating;
    private String body;

    private String imageId;
    private String date;

    private String recipeId;
}
