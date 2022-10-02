package com.fridgerescuer.springboot.data.dto;

import com.fridgerescuer.springboot.data.entity.Member;
import lombok.*;
import org.bson.types.Binary;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private String id;
    private String name;
    private String type;
    private Set<String> ingredientIds;

    private String producerMemberId;
    private String imageId;

    private List<CommentDTO> comments;

    private double ratingAvg;
    private double ratingTotalSum;
}
