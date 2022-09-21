package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.security.dto.AuthorityDto;
import com.fridgerescuer.springboot.security.entity.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(componentModel = "spring", uses = {})
public interface AuthorityMapper {

    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

    AuthorityDto authorityToDTO(Authority authority);

    Authority DTOtoAuthority(AuthorityDto authorityDTO);
}
