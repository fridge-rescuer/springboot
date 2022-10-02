package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.mapper.RecipeMapper;
import com.fridgerescuer.springboot.exception.exceptionimpl.MemberException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ExpirationDataDao expirationDataDao;

    @Test
    void deleteExpirationDataFromMember(){

        //given
        MemberDTO member = MemberDTO.builder().name("야생마").build();

        IngredientDTO ingredientDTO1 =  ingredientDao.save(IngredientDTO.builder().name("마늘").build());
        IngredientDTO ingredientDTO2 =  ingredientDao.save(IngredientDTO.builder().name("사과").build());

        LocalDate now = LocalDate.now();

        List<ExpirationDataDTO> expirationDataDTOList = new ArrayList<>();
        ExpirationDataDTO garlicData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO1).expirationDate(now.plusDays(21)).isNoExpiration(false).build();
        ExpirationDataDTO appleData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO2).expirationDate(now.plusDays(14)).isNoExpiration(false).build();
        expirationDataDTOList.add(garlicData);
        expirationDataDTOList.add(appleData);

        //when
        MemberDTO saveMember = memberDao.saveMember(member);
        memberDao.addExpirationDataToMember(saveMember.getId(), expirationDataDTOList);

        //then
        MemberDTO foundMember = memberDao.findById(saveMember.getId());
        List<ExpirationDataDTO> responseExpirationDataList = foundMember.getExpirationDataDTOList();

        for (int i=0; i<responseExpirationDataList.size() ; ++i ){
            System.out.println(responseExpirationDataList.get(i));
        }

        ExpirationDataDTO targetExpirationDataDTO = responseExpirationDataList.get(0);
        expirationDataDao.deleteExpirationDataById(targetExpirationDataDTO.getId());

        //반영 확인을 위해 다시 불러오기
        foundMember = memberDao.findById(saveMember.getId());
        responseExpirationDataList = foundMember.getExpirationDataDTOList();

        System.out.println("size: " + responseExpirationDataList.size());
        //제거 됨을 확인
        for (int i=0; i<responseExpirationDataList.size() ; ++i ){
            assertThat(responseExpirationDataList.get(i).getId()).isNotEqualTo(targetExpirationDataDTO.getId());
        }

    }

    @Test
    void addExpirationDataToMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("야생마").build();

        IngredientDTO ingredientDTO1 =  ingredientDao.save(IngredientDTO.builder().name("마늘").build());
        IngredientDTO ingredientDTO2 =  ingredientDao.save(IngredientDTO.builder().name("사과").build());

        LocalDate now = LocalDate.now();

        List<ExpirationDataDTO> expirationDataDTOList = new ArrayList<>();
        ExpirationDataDTO garlicData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO1).expirationDate(now.plusDays(21)).isNoExpiration(false).build();
        ExpirationDataDTO appleData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO2).expirationDate(now.plusDays(14)).isNoExpiration(false).build();
        expirationDataDTOList.add(garlicData);
        expirationDataDTOList.add(appleData);

        //when
        MemberDTO saveMember = memberDao.saveMember(member);
        memberDao.addExpirationDataToMember(saveMember.getId(), expirationDataDTOList);

        //then
        MemberDTO foundMember = memberDao.findById(saveMember.getId());
        List<ExpirationDataDTO> responseExpirationDataList = foundMember.getExpirationDataDTOList();
        System.out.println("size: " + responseExpirationDataList.size());

        for (int i=0; i<responseExpirationDataList.size() ; ++i ){
            assertThat(responseExpirationDataList.get(i).getIngredientDTO().getName()).isEqualTo(expirationDataDTOList.get(i).getIngredientDTO().getName());
            assertThat(responseExpirationDataList.get(i).getExpirationDate()).isEqualTo(expirationDataDTOList.get(i).getExpirationDate());
        }
    }

    @Test
    void deletePrivateExpirationDataFromMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("야생마").build();

        IngredientDTO ingredientDTO1 =  ingredientDao.save(IngredientDTO.builder().name("마늘").build());
        IngredientDTO ingredientDTO2 =  ingredientDao.save(IngredientDTO.builder().name("사과").build());

        LocalDate now = LocalDate.now();

        List<ExpirationDataDTO> expirationDataDTOList = new ArrayList<>();
        ExpirationDataDTO garlicData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO1).expirationDate(now.plusDays(21)).isNoExpiration(false).build();
        ExpirationDataDTO appleData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO2).expirationDate(now.plusDays(14)).isNoExpiration(false).build();
        expirationDataDTOList.add(garlicData);
        expirationDataDTOList.add(appleData);

        //when
        MemberDTO saveMember = memberDao.saveMember(member);
        memberDao.addPrivateExpirationDataToMember(saveMember.getId(), expirationDataDTOList);

        //then
        MemberDTO foundMember = memberDao.findById(saveMember.getId());
        List<ExpirationDataDTO> responseExpirationDataList = foundMember.getPrivateExpirationDataDTOList();

        for (int i=0; i<responseExpirationDataList.size() ; ++i ){
            System.out.println(responseExpirationDataList.get(i));
        }

        ExpirationDataDTO targetExpirationDataDTO = responseExpirationDataList.get(0);
        expirationDataDao.deletePrivateExpirationDataById(targetExpirationDataDTO.getId());

        //반영 확인을 위해 다시 불러오기
        foundMember = memberDao.findById(saveMember.getId());
        responseExpirationDataList = foundMember.getPrivateExpirationDataDTOList();

        System.out.println("size: " + responseExpirationDataList.size());
        //제거 됨을 확인
        for (int i=0; i<responseExpirationDataList.size() ; ++i ){
            assertThat(responseExpirationDataList.get(i).getId()).isNotEqualTo(targetExpirationDataDTO.getId());
        }

    }

    @Test
    void addPrivateExpirationDataToMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("야생마").build();

        IngredientDTO ingredientDTO1 =  IngredientDTO.builder().name("마늘").build();
        IngredientDTO ingredientDTO2 =  IngredientDTO.builder().name("사과").build();

        LocalDate now = LocalDate.now();

        List<ExpirationDataDTO> expirationDataDTOList = new ArrayList<>();
        ExpirationDataDTO garlicData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO1).expirationDate(now.plusDays(21)).isNoExpiration(false).build();
        ExpirationDataDTO appleData = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO2).expirationDate(now.plusDays(14)).isNoExpiration(false).build();
        expirationDataDTOList.add(garlicData);
        expirationDataDTOList.add(appleData);

        //when
        MemberDTO saveMember = memberDao.saveMember(member);
        memberDao.addPrivateExpirationDataToMember(saveMember.getId(), expirationDataDTOList);

        //then
        MemberDTO foundMember = memberDao.findById(saveMember.getId());
        List<ExpirationDataDTO> responseExpirationDataList = foundMember.getPrivateExpirationDataDTOList();

        System.out.println("size= " + responseExpirationDataList.size());
        for (int i=0; i<responseExpirationDataList.size() ; ++i ){
            System.out.println(" " + responseExpirationDataList.get(i));
            assertThat(responseExpirationDataList.get(i).getIngredientDTO().getName()).isEqualTo(expirationDataDTOList.get(i).getIngredientDTO().getName());
            assertThat(responseExpirationDataList.get(i).getIngredientDTO().getId()).isEqualTo(null);   //private는 id없음
            assertThat(responseExpirationDataList.get(i).getExpirationDate()).isEqualTo(expirationDataDTOList.get(i).getExpirationDate());
        }
    }



     @Test
    void getCommentsOfMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("모구모구").build();
        RecipeDTO recipe = RecipeDTO.builder().name("보쌈").build();
        CommentDTO comment1 = CommentDTO.builder().rating(1).build();
        CommentDTO comment2 = CommentDTO.builder().rating(2).build();

        //when
        MemberDTO memberResponseDTO = memberDao.saveMember(member);
        RecipeDTO recipeResponseDTO = recipeDao.save(recipe);

        List<CommentDTO> commentResponseDTOs = new ArrayList<>();
        commentResponseDTOs.add(commentDao.save(memberResponseDTO.getId(), recipeResponseDTO.getId(), comment1));
        commentResponseDTOs.add(commentDao.save(memberResponseDTO.getId(), recipeResponseDTO.getId(), comment2));

        //then
         MemberDTO foundMember = memberDao.findById(memberResponseDTO.getId());
         List<CommentDTO> comments = foundMember.getCommentDTOs();

         for (int i = 0; i < comments.size(); i++) {
            assertThat(comments.get(i).getId()).isEqualTo(commentResponseDTOs.get(i).getId());
            assertThat(comments.get(i).getRating()).isEqualTo(commentResponseDTOs.get(i).getRating());
         }
    }

    @Test
    void deleteMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("호날두").build();

        //when
        MemberDTO memberResponseDto = memberDao.saveMember(member);

        String targetId = memberResponseDto.getId();
        memberDao.deleteMemberById(targetId);
        //then
        assertThatThrownBy(() -> memberDao.findById(targetId))
                .isInstanceOf(MemberException.class);

        assertThatThrownBy(() -> memberDao.deleteMemberById(targetId))
                .isInstanceOf(MemberException.class);

    }

    @Test
    void updateMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("호날두").build();
        MemberDTO updateDataDTO = MemberDTO.builder().name("메시").build();

        //when
        MemberDTO memberResponseDto = memberDao.saveMember(member);
        memberDao.updateMemberById(memberResponseDto.getId(), updateDataDTO);

        //then
        assertThat(memberDao.findById(memberResponseDto.getId()).getName()).isEqualTo("메시");
    }

    @Test
    void memberSave(){
        //given
        MemberDTO member = MemberDTO.builder().name("종원").build();

        //when
        MemberDTO memberResponseDto = memberDao.saveMember(member);

        //then
        assertThat(memberResponseDto.getName()).isEqualTo("종원");
        System.out.println("memberResponseDto = " + memberResponseDto);
    }


    @Test
    @DisplayName("멤버가 만든 레시피 등록, 레시피에는 만든 멤버를 등록")
    void saveRecipeByMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("우왁굳").build();
        IngredientDTO ingredient = IngredientDTO.builder().name("감자").build();

        //when
        MemberDTO memberResponseDto = memberDao.saveMember(member);
        IngredientDTO savedIngredient = ingredientDao.save(ingredient);

        RecipeDTO recipe = RecipeDTO.builder().name("감자 튀김").type("튀김")
                .ingredientIds(new HashSet<>(Arrays.asList(savedIngredient.getId()))).build();
        RecipeDTO recipeResponseDTO = recipeDao.saveRecipeByMemberId(memberResponseDto.getId(), recipe);

        //then
        RecipeDTO savedRecipe = recipeDao.findById(recipeResponseDTO.getId());
        MemberDTO savedMember = memberDao.findById(memberResponseDto.getId());

        List<RecipeDTO> memberRecipes = savedMember.getRecipeDTOs();
        for (RecipeDTO responseDTO: memberRecipes) {
            System.out.println("  - responseMember : " + responseDTO);
        }

        List<RecipeDTO> recipeResponseDTOs = memberDao.findById(memberResponseDto.getId()).getRecipeDTOs();
        RecipeDTO referenceRecipe = recipeResponseDTOs.get(0);

        System.out.println("referenceRecipe = " + referenceRecipe);


        assertThat(referenceRecipe.getName()).isEqualTo("감자 튀김");
        assertThat(referenceRecipe.getProducerMemberId()).isEqualTo(memberResponseDto.getId());

        //존재하지 않는 id로 접근시
        assertThatThrownBy(() -> recipeDao.saveRecipeByMemberId("123456", recipe))
                .isInstanceOf(MemberException.class);
    }

}