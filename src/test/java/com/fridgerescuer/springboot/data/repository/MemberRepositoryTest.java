package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchMemberException;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.MemberService;
import com.fridgerescuer.springboot.service.RecipeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class MemberRepositoryTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void beforeEach(){
        memberRepository.deleteAll();
        ingredientRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    //given
    //when
    //then

    @Test
    void deleteMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("호날두").build();

        //when
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);

        String targetId = memberResponseDto.getId();
        memberService.deleteMemberById(targetId);

        //then
        assertThatThrownBy(() -> memberService.findMemberById(targetId))
                .isInstanceOf(NoSuchMemberException.class);

        assertThatThrownBy(() -> memberService.deleteMemberById(targetId))
                .isInstanceOf(NoSuchMemberException.class);

    }

    @Test
    void updateMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("호날두").build();
        MemberDTO updateDataDTO = MemberDTO.builder().name("메시").build();

        //when
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);
        memberService.updateMemberById(memberResponseDto.getId(), updateDataDTO);

        //then
        assertThat(memberService.findMemberById(memberResponseDto.getId()).getName()).isEqualTo("메시");
    }

    @Test
    void memberSave(){
        //given
        MemberDTO member = MemberDTO.builder().name("종원").build();

        //when
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);

        //then
        assertThat(memberResponseDto.getName()).isEqualTo("종원");
        System.out.println("memberResponseDto = " + memberResponseDto);
    }

    @Test
    void addIngredientsToMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("종원").build();
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);

        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").type("채소").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").type("식용유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("계란").type("유제품").build();
        IngredientDTO ingredient4 = IngredientDTO.builder().name("고추").type("채소").build();


        //when

        List<IngredientResponseDTO> responseDTOList = new ArrayList<>();
        responseDTOList.add(ingredientService.saveIngredient(ingredient1));
        responseDTOList.add(ingredientService.saveIngredient(ingredient2));
        responseDTOList.add(ingredientService.saveIngredient(ingredient3));
        responseDTOList.add(ingredientService.saveIngredient(ingredient4));

        memberService.addIngredientsToMember(memberResponseDto.getId(), IngredientMapper.INSTANCE.responseDTOListToDTOList(responseDTOList));

        //then
        MemberResponseDTO findMember = memberService.findMemberById(memberResponseDto.getId());
        System.out.println("findMember = " + findMember);

        assertThat(findMember.getIngredientDTOs().size()).isEqualTo(responseDTOList.size());

        for (IngredientDTO ingredientDTO: findMember.getIngredientDTOs() ) {
            System.out.println("ingredientDTO = " + ingredientDTO);
        }
    }

    @Test
    @DisplayName("멤버가 레시피 등록")
    void saveRecipeByMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("우왁굳").build();
        RecipeDTO recipe = RecipeDTO.builder().name("감자 튀김").type("튀김").ingredientNames(new String[]{}).build();

        //when
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);
        recipeService.saveRecipeByMember(memberResponseDto.getId(), recipe);

        //then
        List<RecipeDTO> recipeDTOs = memberService.findMemberById(memberResponseDto.getId()).getRecipeDTOs();
        assertThat(recipeDTOs.get(0).getName()).isEqualTo("감자 튀김");

        //존재하지 않는 id로 접근시
        assertThatThrownBy(() -> recipeService.saveRecipeByMember("123456", recipe))
                .isInstanceOf(NoSuchMemberException.class);
    }
}