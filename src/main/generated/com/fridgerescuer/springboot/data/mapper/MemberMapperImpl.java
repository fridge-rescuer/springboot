package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;
import com.fridgerescuer.springboot.data.entity.Member;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-11T17:41:01+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDTO memberToMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTO.MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.name( member.getName() );

        return memberDTO.build();
    }

    @Override
    public Member memberDtoToMember(MemberDTO memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.name( memberDto.getName() );

        return member.build();
    }

    @Override
    public MemberResponseDTO memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDTO.MemberResponseDTOBuilder memberResponseDTO = MemberResponseDTO.builder();

        memberResponseDTO.id( member.getId() );
        memberResponseDTO.name( member.getName() );

        return memberResponseDTO.build();
    }
}
