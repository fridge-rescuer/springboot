package com.fridgerescuer.springboot.exception.errorcodeimpl;

import com.fridgerescuer.springboot.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberError implements ErrorCode {

    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 존재하는 ID입니다"),
    NOT_EXIST(HttpStatus.FORBIDDEN, "존재하지 않는 사용자입니다");


    private final HttpStatus httpStatus;
    private final String message;
}