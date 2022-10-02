package com.fridgerescuer.springboot.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Document(collection = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Comment {
    @Id
    @GeneratedValue
    private String id;

    private double rating;      //0~5 점까지 0.5단위로 매길수 있음
    private String body;

    private String imageId;
    private LocalDateTime date;

    private String recipeId;

}
