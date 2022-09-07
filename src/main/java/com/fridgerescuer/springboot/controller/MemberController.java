package com.fridgerescuer.springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
/*
    @Autowired
    private final MemberService memberService;

    @PostMapping("/join")
    public String memberJoin(@RequestBody @Valid MemberDTO memberToJoin) {
        memberService.memberJoin(memberToJoin);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public MemberResponseDTO memberLogin(@Valid @ModelAttribute LoginForm loginForm) {
        return memberService.memberLogin(loginForm.getId(), loginForm.getPassword());
    }
    */
}
