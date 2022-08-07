package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.MemberDto;
import com.fridgerescuer.springboot.data.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto saveMember(MemberDto memberDto);
}
