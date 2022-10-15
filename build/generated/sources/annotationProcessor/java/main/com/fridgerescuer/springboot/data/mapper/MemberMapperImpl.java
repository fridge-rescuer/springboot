package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.vo.MemberVO;
import com.fridgerescuer.springboot.security.dto.AuthorityDTO;
import com.fridgerescuer.springboot.security.entity.Authority;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-10T23:40:39+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.1 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
    private final ExpirationDataMapper expirationDataMapper = ExpirationDataMapper.INSTANCE;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;
    private final AuthorityMapper authorityMapper = AuthorityMapper.INSTANCE;

    @Override
    public MemberDTO memberToDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTO.MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.expirationDataDTOList( expirationDataMapper.dataListToDTOList( member.getExpirationDataList() ) );
        memberDTO.privateExpirationDataDTOList( expirationDataMapper.privateDataListToDTOList( member.getPrivateExpirationDataList() ) );
        memberDTO.recipeDTOs( recipeMapper.recipeListToDTOList( member.getRecipes() ) );
        memberDTO.commentDTOs( commentMapper.commentListToDTOList( member.getComments() ) );
        memberDTO.authorityDtoSet( authoritySetToAuthorityDTOSet( member.getAuthorities() ) );
        memberDTO.id( member.getId() );
        memberDTO.name( member.getName() );
        memberDTO.password( member.getPassword() );

        return memberDTO.build();
    }

    @Override
    public Member DtoToMember(MemberDTO memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.expirationDataList( expirationDataMapper.DTOListToDataList( memberDto.getExpirationDataDTOList() ) );
        member.privateExpirationDataList( expirationDataMapper.DTOListToPrivateDataList( memberDto.getPrivateExpirationDataDTOList() ) );
        member.recipes( recipeDTOListToRecipeList( memberDto.getRecipeDTOs() ) );
        member.comments( commentMapper.DTOListToCommentList( memberDto.getCommentDTOs() ) );
        member.authorities( authorityDTOSetToAuthoritySet( memberDto.getAuthorityDtoSet() ) );
        member.id( memberDto.getId() );
        member.password( memberDto.getPassword() );
        member.name( memberDto.getName() );

        return member.build();
    }

    @Override
    public MemberVO DtoToMemberVO(MemberDTO memberDTO) {
        if ( memberDTO == null ) {
            return null;
        }

        List<ExpirationDataDTO> expirationDataDTOList = null;
        List<ExpirationDataDTO> privateExpirationDataDTOList = null;
        List<RecipeDTO> recipeDTOs = null;
        String id = null;
        String password = null;
        String name = null;

        List<ExpirationDataDTO> list = memberDTO.getExpirationDataDTOList();
        if ( list != null ) {
            expirationDataDTOList = new ArrayList<ExpirationDataDTO>( list );
        }
        List<ExpirationDataDTO> list1 = memberDTO.getPrivateExpirationDataDTOList();
        if ( list1 != null ) {
            privateExpirationDataDTOList = new ArrayList<ExpirationDataDTO>( list1 );
        }
        List<RecipeDTO> list2 = memberDTO.getRecipeDTOs();
        if ( list2 != null ) {
            recipeDTOs = new ArrayList<RecipeDTO>( list2 );
        }
        id = memberDTO.getId();
        password = memberDTO.getPassword();
        name = memberDTO.getName();

        MemberVO memberVO = new MemberVO( id, password, name, expirationDataDTOList, privateExpirationDataDTOList, recipeDTOs );

        return memberVO;
    }

    protected Set<AuthorityDTO> authoritySetToAuthorityDTOSet(Set<Authority> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorityDTO> set1 = new LinkedHashSet<AuthorityDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Authority authority : set ) {
            set1.add( authorityMapper.authorityToDTO( authority ) );
        }

        return set1;
    }

    protected List<Recipe> recipeDTOListToRecipeList(List<RecipeDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Recipe> list1 = new ArrayList<Recipe>( list.size() );
        for ( RecipeDTO recipeDTO : list ) {
            list1.add( recipeMapper.DTOtoRecipe( recipeDTO ) );
        }

        return list1;
    }

    protected Set<Authority> authorityDTOSetToAuthoritySet(Set<AuthorityDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Authority> set1 = new LinkedHashSet<Authority>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : set ) {
            set1.add( authorityMapper.DTOtoAuthority( authorityDTO ) );
        }

        return set1;
    }
}
