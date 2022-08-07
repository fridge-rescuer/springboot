package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.MemberDto;
import com.fridgerescuer.springboot.data.dto.MemberResponseDto;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.mapper.MemberMapper;
import com.fridgerescuer.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired private final MemberDao memberDao;

    @Override
    public MemberResponseDto saveMember(MemberDto memberDto) {
        Member savedMember = memberDao.saveMember(MemberMapper.INSTANCE.memberDtoToMember(memberDto));
        return MemberMapper.INSTANCE.memberToMemberResponseDto(savedMember);
    }
}
