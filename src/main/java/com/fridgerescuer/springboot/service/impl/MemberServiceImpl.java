package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDto;
import com.fridgerescuer.springboot.data.dto.MemberResponseDto;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.data.mapper.MemberMapper;
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
    public MemberResponseDto saveMember(MemberDto memberDto) {
        Member savedMember = memberDao.saveMember(MemberMapper.INSTANCE.memberDtoToMember(memberDto));
        return MemberMapper.INSTANCE.memberToMemberResponseDto(savedMember);
    }

    @Override
    public MemberResponseDto findMemberById(String memberId) {
        Member findMember = memberDao.findById(memberId);

        MemberResponseDto memberResponseDto = MemberMapper.INSTANCE.memberToMemberResponseDto(findMember);  //재료 리스트는 타입을 변환해 주입해줘
        memberResponseDto.setIngredientDTOs(IngredientMapper.INSTANCE.ingredientListToDtoList(findMember.getIngredients()));

        return memberResponseDto;
    }

    @Override
    public void addIngredientsToMember(String memberId, List<IngredientDTO> ingredientDTOs) {
        memberDao.addIngredientsToMember(memberId, IngredientMapper.INSTANCE.ingredientListToIngredientDTOList(ingredientDTOs));
    }
}
