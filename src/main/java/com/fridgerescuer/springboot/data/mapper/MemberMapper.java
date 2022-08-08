package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.MemberDto;
import com.fridgerescuer.springboot.data.dto.MemberResponseDto;
import com.fridgerescuer.springboot.data.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDto memberToMemberDto(Member member);
    Member memberDtoToMember(MemberDto memberDto);

    MemberResponseDto memberToMemberResponseDto(Member member);
}
