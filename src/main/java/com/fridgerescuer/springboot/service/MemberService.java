package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDto;
import com.fridgerescuer.springboot.data.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {
    MemberResponseDto saveMember(MemberDto memberDto);
    MemberResponseDto findMemberById(String memberId);
    void addIngredientsToMember(String memberId, List<IngredientDTO> ingredientDTOs);
}
