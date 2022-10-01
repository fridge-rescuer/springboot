package com.fridgerescuer.springboot.data.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String id;

    private double rating;
    private String body;

    private String imageId;
    private LocalDateTime date;

    private String recipeId;

}
