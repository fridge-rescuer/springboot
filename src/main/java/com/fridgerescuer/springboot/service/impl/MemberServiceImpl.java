package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.CommentResponseDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.mapper.CommentMapper;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.data.mapper.MemberMapper;
import com.fridgerescuer.springboot.exception.errorcodeimpl.MemberError;
import com.fridgerescuer.springboot.exception.exceptionimpl.MemberException;
import com.fridgerescuer.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired private final MemberDao memberDao;

    @Override
    public void saveMember(MemberDTO memberDto) {
        memberDao.saveMember(MemberMapper.INSTANCE.DtoToMember(memberDto));
    }

    @Override
    public MemberDTO findMemberById(String memberId) {
        MemberDTO memberDTO =  MemberMapper.INSTANCE.memberToDto(memberDao.findById(memberId));
        if(memberDTO.getIngredientDTOs().isEmpty())
            memberDTO.setIngredientDTOs(null);
        if(memberDTO.getRecipeDTOs().isEmpty())
            memberDTO.setRecipeDTOs(null);

        return memberDTO;
    }

    @Override
    public List<CommentResponseDTO> getCommentsByMemberId(String memberId) {
        Member member = memberDao.findById(memberId);

        return CommentMapper.INSTANCE.commentListToResponseDTOList(member.getComments());
    }

    @Override
    public void addIngredientsToMember(String memberId, List<IngredientDTO> ingredientDTOs) {
        memberDao.addIngredientsToMember(memberId, IngredientMapper.INSTANCE.ingredientListToDTOList(ingredientDTOs));
    }

    @Override
    public void addIngredientsToMemberByIngredientIds(String memberId, List<String> ingredientIds) {
        memberDao.addIngredientsToMemberByIngredientIds(memberId, ingredientIds);
    }

    @Override
    public void addIngredientsAndExpirationDataToMember(String memberId, List<String> ingredientIds, List<ExpirationData> expirationDataList) {
        memberDao.addIngredientAndExpirationDataToMember(memberId,ingredientIds,expirationDataList);
    }

    @Override
    public void updateMemberById(String memberId, MemberDTO updateDataMemberDTO) {
        memberDao.updateMemberById(memberId, updateDataMemberDTO);
    }

    @Override
    public void deleteMemberById(String id) {
        memberDao.deleteMemberById(id);
    }

    /**
     *
     * @param loginId
     * @param loginPassword
     * @return MemberResponseDTO if (loginId, loginPassword) is found
     * @exception MemberException if not found
     */
    @Override
    public MemberResponseDTO memberLogin(String loginId, String loginPassword) {
        MemberDTO tryLoginMember = findMemberById(loginId);

        if(!tryLoginMember.getPassword().equals(loginPassword)) {
            throw new MemberException(MemberError.INCORRECT_PASSWORD);
        }

        return MemberMapper.INSTANCE.DtoToMemberResponseDto(tryLoginMember);
    }

    /**
     * 중복 아이디를 검사하고, 중복된 데이터가 아니라면 DB에 저장
     * @param memberToJoin
     * @return
     */
    @Override
    public MemberResponseDTO memberJoin(MemberDTO memberToJoin) {

        memberDuplicateCheck(memberToJoin.getId());

        saveMember(memberToJoin);

        return MemberMapper.INSTANCE.DtoToMemberResponseDto(findMemberById(memberToJoin.getId()));
    }

    @Override
    public void memberDuplicateCheck(String id) {
        try {
            findMemberById(id);
            throw new MemberException(MemberError.DUPLICATE_ID);
        } catch (MemberException e) {
            return;
        }
    }
}
