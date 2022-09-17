package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dto.LoginForm;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.vo.MemberVO;
import com.fridgerescuer.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GlobalController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/join")
    public String memberJoin(@RequestBody @Valid MemberDTO memberToJoin) {
        memberService.memberJoin(memberToJoin);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public MemberVO memberLogin(@Valid @RequestBody LoginForm loginForm) {
        return memberService.memberLogin(loginForm.getId(), loginForm.getPassword());
    }

}
