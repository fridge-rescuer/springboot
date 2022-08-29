package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.data.mapper.RecipeMapper;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchMemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private CommentService commentService;

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
    void getCommentsOfMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("모구모구").build();
        RecipeDTO recipe = RecipeDTO.builder().name("보쌈").build();
        CommentDTO comment1 = CommentDTO.builder().rating(1).build();
        CommentDTO comment2 = CommentDTO.builder().rating(2).build();

        //when
        MemberResponseDTO memberResponseDTO = memberService.saveMember(member);
        RecipeResponseDTO recipeResponseDTO = recipeService.saveRecipe(recipe);
        List<CommentResponseDTO> commentResponseDTOs = new ArrayList<>();
        commentResponseDTOs.add(commentService.saveComment(memberResponseDTO.getId(), recipeResponseDTO.getId(), comment1));
        commentResponseDTOs.add(commentService.saveComment(memberResponseDTO.getId(), recipeResponseDTO.getId(), comment2));

        //then
        List<CommentResponseDTO> comments = memberService.getCommentsByMemberId(memberResponseDTO.getId());

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

        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("계란").build();
        IngredientDTO ingredient4 = IngredientDTO.builder().name("고추").build();


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

        assertThat(findMember.getIngredientResponseDTOs().size()).isEqualTo(responseDTOList.size());

        for (IngredientResponseDTO ingredientResponseDTO: findMember.getIngredientResponseDTOs() ) {
            System.out.println("ingredientDTO = " + ingredientResponseDTO);
        }
    }

    @Test
    void addIngredientsToMemberByIngredientIds(){
        //given
        MemberDTO member = MemberDTO.builder().name("종원").build();
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);

        IngredientResponseDTO ingredient1 = ingredientService.saveIngredient(IngredientDTO.builder().name("마늘").build());
        IngredientResponseDTO ingredient2 = ingredientService.saveIngredient(IngredientDTO.builder().name("올리브유").build());
        IngredientResponseDTO ingredient3 = ingredientService.saveIngredient(IngredientDTO.builder().name("김계란").build());
        IngredientResponseDTO ingredient4 = ingredientService.saveIngredient(IngredientDTO.builder().name("고추").build());


        //when
        List<String> responseIds = new ArrayList<>();
        responseIds.add(ingredient1.getId());
        responseIds.add(ingredient2.getId());
        responseIds.add(ingredient3.getId());
        responseIds.add(ingredient4.getId());

        memberService.addIngredientsToMemberByIngredientIds(memberResponseDto.getId(), responseIds);

        //then
        MemberResponseDTO findMember = memberService.findMemberById(memberResponseDto.getId());
        System.out.println("findMember = " + findMember);

        assertThat(findMember.getIngredientResponseDTOs().size()).isEqualTo(responseIds.size());

        for (IngredientResponseDTO ingredientResponseDTO: findMember.getIngredientResponseDTOs() ) {
            System.out.println("ingredientDTO = " + ingredientResponseDTO);
        }
    }


    @Test
    @DisplayName("멤버가 만든 레시피 등록, 레시피에는 만든 멤버를 등록")
    void saveRecipeByMember(){
        //given
        MemberDTO member = MemberDTO.builder().name("우왁굳").build();
        IngredientDTO ingredient = IngredientDTO.builder().name("감자").build();
        RecipeDTO recipe = RecipeDTO.builder().name("감자 튀김").type("튀김").ingredientNames(new String[]{"감자"}).build();

        //when
        MemberResponseDTO memberResponseDto = memberService.saveMember(member);
        ingredientService.saveIngredient(ingredient);
        RecipeResponseDTO recipeResponseDTO = recipeService.saveRecipeByMember(memberResponseDto.getId(), recipe);

        //then
        Recipe savedRecipe = recipeRepository.findById(recipeResponseDTO.getId()).get();
        Member savedMember = memberRepository.findById(memberResponseDto.getId()).get();

        List<Recipe> memberRecipes = savedMember.getRecipes();
        for (RecipeResponseDTO responseDTO: RecipeMapper.INSTANCE.recipeListToResponseDTOList(memberRecipes)) {
            System.out.println("  - responseMember : " + responseDTO);
        }

        List<RecipeResponseDTO> recipeResponseDTOs = memberService.findMemberById(memberResponseDto.getId()).getRecipeResponseDTOs();
        RecipeResponseDTO referenceRecipe = recipeResponseDTOs.get(0);

        System.out.println("referenceRecipe = " + referenceRecipe);


        assertThat(referenceRecipe.getName()).isEqualTo("감자 튀김");
        assertThat(referenceRecipe.getProducerMemberId()).isEqualTo(memberResponseDto.getId());

        //존재하지 않는 id로 접근시
        assertThatThrownBy(() -> recipeService.saveRecipeByMember("123456", recipe))
                .isInstanceOf(NoSuchMemberException.class);
    }
}