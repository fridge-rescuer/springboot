package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;

import java.util.List;

public interface MemberService {
    void saveMember(MemberDTO memberDto);
    MemberDTO findMemberById(String memberId);
    MemberResponseDTO memberLogin(String loginId, String loginPassword);
    MemberResponseDTO memberJoin(MemberDTO memberToJoin);
    void addIngredientsToMember(String memberId, List<IngredientDTO> ingredientDTOs);
    void updateMemberById(String memberId, MemberDTO updateDataMemberDTO);

    void deleteMemberById(String id);

    void memberDuplicateCheck(String id);
}
