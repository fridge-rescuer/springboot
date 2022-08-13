package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;

import java.util.List;

public interface MemberService {
    MemberResponseDTO saveMember(MemberDTO memberDto);
    MemberResponseDTO findMemberById(String memberId);

    void addIngredientsToMember(String memberId, List<IngredientDTO> ingredientDTOs);
    void updateMemberById(String memberId, MemberDTO updateDataMemberDTO);

    void deleteMemberById(String id);
}
