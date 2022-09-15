package com.fridgerescuer.springboot.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@Getter
@ToString
public class CustomExpirationForm {

    @NotBlank
    private String memberId;

    private String ingredientName;
    private LocalDate newDate;
    private boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true
}