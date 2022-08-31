package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.CommentResponseDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;

import java.util.List;

public interface MemberService {
    MemberResponseDTO saveMember(MemberDTO memberDto);

    MemberResponseDTO findMemberById(String memberId);
    List<CommentResponseDTO> getCommentsByMemberId(String memberId);

    void addIngredientsToMember(String memberId, List<IngredientDTO> ingredientDTOs);
    void addIngredientsToMemberByIngredientIds(String memberId, List<String> ingredientIds);
    void addIngredientsAndExpirationDataToMember(String memberId,  List<String> ingredientIds, List<ExpirationData> expirationDataList);
    void updateMemberById(String memberId, MemberDTO updateDataMemberDTO);

    void deleteMemberById(String id);
}
