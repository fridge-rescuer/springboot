package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.security.dto.AuthorityDTO;
import com.fridgerescuer.springboot.security.entity.Authority;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-10T23:40:39+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.1 (Oracle Corporation)"
)
public class AuthorityMapperImpl implements AuthorityMapper {

    @Override
    public AuthorityDTO authorityToDTO(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityDTO.AuthorityDTOBuilder authorityDTO = AuthorityDTO.builder();

        authorityDTO.authorityName( authority.getAuthorityName() );

        return authorityDTO.build();
    }

    @Override
    public Authority DTOtoAuthority(AuthorityDTO authorityDTO) {
        if ( authorityDTO == null ) {
            return null;
        }

        Authority.AuthorityBuilder authority = Authority.builder();

        authority.authorityName( authorityDTO.getAuthorityName() );

        return authority.build();
    }
}
