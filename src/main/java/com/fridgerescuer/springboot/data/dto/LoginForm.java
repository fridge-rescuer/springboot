package com.fridgerescuer.springboot.data.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginForm {

    @NotBlank
    private String id;

    @NotBlank
    @Pattern(regexp="(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}", message = "영문자, 숫자가 포함된 8자리 이상의 비밀번호를 입력해주세요")
    private String password;
}