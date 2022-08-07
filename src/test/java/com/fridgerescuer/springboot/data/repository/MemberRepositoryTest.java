package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dto.MemberDto;
import com.fridgerescuer.springboot.data.dto.MemberResponseDto;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class MemberRepositoryTest {
    @Autowired
    private MemberService memberService;

    //given
    //when
    //then

    @Test
    void memberCRUD(){
        //given
        MemberDto member = MemberDto.builder().name("종원").build();

        //when
        MemberResponseDto memberResponseDto = memberService.saveMember(member);

        //then
        Assertions.assertThat(memberResponseDto.getName()).isEqualTo("종원");
        System.out.println("memberResponseDto = " + memberResponseDto);
    }
}