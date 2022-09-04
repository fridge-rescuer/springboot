package com.fridgerescuer.springboot.exception.exceptionimpl;

import com.fridgerescuer.springboot.exception.errorcodeimpl.MemberError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberException extends RuntimeException {
    private final MemberError errorCode;
}
