package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.mapper.MemberMapper;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import com.fridgerescuer.springboot.data.vo.MemberVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach(){
        memberRepository.deleteAll();
    }

    @Test
    void memberJoin() throws Exception {
        //given
        MemberDTO memberDTO = MemberDTO.builder().id("tank3a").name("김종원").password("abcd1234").build();
        memberService.memberJoin(memberDTO);
        MemberVO givenMember = MemberMapper.INSTANCE.DtoToMemberVO(memberDTO);

        //when
        MemberVO tryJoinMember  = MemberMapper.INSTANCE.DtoToMemberVO(memberService.findMemberById("tank3a"));

        //then
        assertThat(tryJoinMember).isEqualTo(givenMember);
    }

    @Test
    void memberLogin() {
        //given
        MemberDTO memberDTO = MemberDTO.builder().id("tank3a").name("김종원").password("abcd1234").build();
        memberService.saveMember(memberDTO);
        MemberVO givenMember = MemberMapper.INSTANCE.DtoToMemberVO(memberDTO);

        //when
        MemberVO tryLoginMember = memberService.memberLogin("tank3a", "abcd1234");

        //then
        assertThat(tryLoginMember).isEqualTo(givenMember);
    }
}
