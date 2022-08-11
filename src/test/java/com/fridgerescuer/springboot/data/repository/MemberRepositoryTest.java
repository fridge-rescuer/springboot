package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.MemberResponseDTO;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class MemberRepositoryTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void beforeEach(){
        memberRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    //given
    //when
    //then

    @Test
    void memberSave(){
        //given
        MemberDTO member = MemberDTO.builder().name("종원").build();

        //when
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);

        //then
        Assertions.assertThat(memberResponseDto.getName()).isEqualTo("종원");
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

        Assertions.assertThat(findMember.getIngredientDTOs().size()).isEqualTo(responseDTOList.size());

        for (IngredientDTO ingredientDTO: findMember.getIngredientDTOs() ) {
            System.out.println("ingredientDTO = " + ingredientDTO);
        }
    }
}