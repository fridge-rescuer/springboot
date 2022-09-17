package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.vo.ExpirationDataVO;
import com.fridgerescuer.springboot.data.vo.IngredientVO;
import com.fridgerescuer.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/{memberId}/ingredients")
    public List<ExpirationDataVO> loadMemberIngredients(@PathVariable("memberId") String memberId) {
        return memberService.loadMemberIngredients(memberId);
    }

}
