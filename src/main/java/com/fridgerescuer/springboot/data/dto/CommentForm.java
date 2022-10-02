package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@Getter
@ToString
public class CommentForm {

    @NotBlank
    private String body;

    @Range(min = 0, max = 5)
    @NotBlank
    private double rating;

    private MultipartFile images;
}