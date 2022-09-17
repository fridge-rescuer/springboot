package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.vo.MemberVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-15T13:16:11+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
    private final ExpirationDataMapper expirationDataMapper = ExpirationDataMapper.INSTANCE;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;

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
}
