package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;
import com.fridgerescuer.springboot.data.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDTO memberToDto(Member member);

    Member DtoToMember(MemberDTO memberDto);

    @Mapping(source = "ingredients", target = "ingredientDTOs")
    @Mapping(source = "recipes", target = "recipeDTOs")
    MemberResponseDTO memberToResponseDto(Member member);
}
