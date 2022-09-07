package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.*;

import java.time.LocalDate;
import java.util.List;

public interface MemberDao {
    MemberDTO saveMember(MemberDTO memberDTO);
    MemberDTO findById(String memberId);
    void addExpirationDataToMember(String memberId, List<ExpirationDataDTO> expirationDataDTOList);
    void addPrivateExpirationDataToMember(String memberId, List<ExpirationDataDTO> expirationDataDTOList);

    void addRecipeToMember(String memberId, RecipeDTO recipeDTO);

    void addCommentToMember(String memberId, CommentDTO commentDTO);

    void updateMemberById(String memberId, MemberDTO updateDataMemberDTO);

    void deleteMemberById(String memberId);

}
