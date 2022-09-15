package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.vo.ExpirationDataVO;
import com.fridgerescuer.springboot.data.vo.MemberVO;

import java.util.List;

public interface MemberService {
    void saveMember(MemberDTO memberDto);

    MemberDTO findMemberById(String memberId);

    void updateMemberById(String memberId, MemberDTO updateDataMemberDTO);

    void deleteMemberById(String id);

    MemberVO memberLogin(String loginId, String loginPassword);

    void memberJoin(MemberDTO memberToJoin);

    void memberDuplicateCheck(String id);

    List<ExpirationDataVO> loadMemberIngredients(String memberId);
}
