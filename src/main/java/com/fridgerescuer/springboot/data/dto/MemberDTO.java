package com.fridgerescuer.springboot.data.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    @NotBlank(message = "ID를 입력해주세요")
    private String id;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

//    @Pattern(regexp="(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}", message = "1개 이상의 영문자와 숫자가 포함된 8자리 이상의 비밀번호를 입력해주세요")
    @NotBlank
    private String password;

    private List<IngredientDTO> ingredientDTOs;
    private List<RecipeDTO> recipeDTOs;
    //이름 바꾸면 오류터짐
}
