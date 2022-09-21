package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.security.dto.AuthorityDTO;
import com.fridgerescuer.springboot.security.entity.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper//(componentModel = "spring", uses = {})
public interface AuthorityMapper {

    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

    AuthorityDTO authorityToDTO(Authority authority);

    Authority DTOtoAuthority(AuthorityDTO authorityDTO);
}
