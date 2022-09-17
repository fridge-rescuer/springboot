package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.mapper.ExpirationDataMapper;
import com.fridgerescuer.springboot.data.mapper.MemberMapper;
import com.fridgerescuer.springboot.data.vo.ExpirationDataVO;
import com.fridgerescuer.springboot.data.vo.MemberVO;
import com.fridgerescuer.springboot.exception.errorcodeimpl.MemberError;
import com.fridgerescuer.springboot.exception.exceptionimpl.MemberException;
import com.fridgerescuer.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberDao memberDao;

    @Override
    public void saveMember(MemberDTO memberDto) {
        memberDao.saveMember(memberDto);
    }

    @Override
    public MemberDTO findMemberById(String memberId) {
        MemberDTO memberDTO = memberDao.findById(memberId);

        return memberDTO;
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
     * @param loginId
     * @param loginPassword
     * @return MemberVO if (loginId, loginPassword) is found
     * @throws MemberException if not found
     */

    @Override
    public MemberVO memberLogin(String loginId, String loginPassword) {
        MemberDTO tryLoginMember = findMemberById(loginId);

        if (!tryLoginMember.getPassword().equals(loginPassword)) {
            throw new MemberException(MemberError.INCORRECT_PASSWORD);
        }

        return MemberMapper.INSTANCE.DtoToMemberVO(tryLoginMember);
    }

    /**
     * 중복 아이디를 검사하고, 중복된 데이터가 아니라면 DB에 저장
     *
     * @param memberToJoin
     */
    @Override
    public void memberJoin(MemberDTO memberToJoin) {

        memberDuplicateCheck(memberToJoin.getId());

        saveMember(memberToJoin);
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

    @Override
    public List<ExpirationDataVO> loadMemberIngredients(String memberId) {
        return combineExpirationData(memberId).stream().map(data -> ExpirationDataMapper.INSTANCE.DTOtoVO(data)).collect(Collectors.toList());
    }

    private List<ExpirationDataDTO> combineExpirationData(String memberId) {
        MemberDTO memberDTO = memberDao.findById(memberId);

        List<ExpirationDataDTO> list = new ArrayList<>();
        list.addAll(memberDTO.getExpirationDataDTOList());
        list.addAll(memberDTO.getPrivateExpirationDataDTOList());

        return list;
    }


//    @Override
//    public List<CommentResponseDTO> getCommentsByMemberId(String memberId) {
//        Member member = memberDao.findById(memberId);
//
//        return CommentMapper.INSTANCE.commentListToResponseDTOList(member.getComments());
//    }

//    @Override
//    public void addIngredientsToMember(String memberId, List<IngredientDTO> ingredientDTOs) {
//        memberDao.addIngredientsToMember(memberId, IngredientMapper.INSTANCE.ingredientListToDTOList(ingredientDTOs));
//    }

//    @Override
//    public void addIngredientsToMemberByIngredientIds(String memberId, List<String> ingredientIds) {
//        memberDao.addIngredientsToMemberByIngredientIds(memberId, ingredientIds);
//    }
//
//    @Override
//    public void addIngredientsAndExpirationDataToMember(String memberId, List<String> ingredientIds, List<ExpirationData> expirationDataList) {
//        memberDao.addIngredientAndExpirationDataToMember(memberId,ingredientIds,expirationDataList);
//    }

}
