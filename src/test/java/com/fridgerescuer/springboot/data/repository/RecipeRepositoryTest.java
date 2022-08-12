package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.mapper.RecipeMapper;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private MemberService memberService;


    @BeforeEach
    void beforeEach() {  //모두 롤백
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    //given
    //when
    //then

    @Autowired
    private RecipeDao recipeDao;

    @Test
    void recipeUpdateAndDelete(){
        //given
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").type("채소").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").type("식용유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("고추").type("채소").build();
        IngredientDTO ingredient4 = IngredientDTO.builder().name("감자").type("채소").build();

        RecipeDTO recipe = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘","올리브유","고추"}).build();
        RecipeDTO updateDateRecipe = RecipeDTO.builder().name("감자 튀김").type("튀김").ingredientNames(new String[]{"감자"}).build();

        //when
        ingredientService.saveIngredient(ingredient1);
        ingredientService.saveIngredient(ingredient2);
        ingredientService.saveIngredient(ingredient3);
        ingredientService.saveIngredient(ingredient4);

        RecipeResponseDTO recipeResponseDTO = recipeService.saveRecipe(recipe);
        recipeService.updateRecipeById(recipeResponseDTO.getId(), updateDateRecipe);

        //then
        RecipeResponseDTO updatedRecipe = recipeService.findById(recipeResponseDTO.getId());
        assertThat(updatedRecipe.getName()).isEqualTo("감자 튀김");     // 내용 update 확인
        assertThat(ingredientRepository.findByName("마늘").getRecipes().size()).isEqualTo(0); //연관관계 제거 확인
        assertThat(ingredientRepository.findByName("감자").getRecipes().size()).isEqualTo(1); // 연관관계 재 생성 확인

        System.out.println("updatedRecipe = " + updatedRecipe);
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
        Assertions.assertThat(recipeDTOs.get(0).getName()).isEqualTo("감자 튀김");
    }

    @Test
    @DisplayName("해당 이름을 포함하는 모든 레시피 찾기")
    void findAllRecipesByContainName(){
        //given
        RecipeDTO recipe1 = RecipeDTO.builder().name("감자 튀김").type("튀김").ingredientNames(new String[]{}).build();
        RecipeDTO recipe2 = RecipeDTO.builder().name("새우튀김").type("튀김").ingredientNames(new String[]{}).build();
        RecipeDTO recipe3 = RecipeDTO.builder().name("병아리 튀김").type("튀김").ingredientNames(new String[]{}).build();

        //when
        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);
        recipeService.saveRecipe(recipe3);

        //then
        List<RecipeResponseDTO> fryRecipes = recipeService.findAllRecipesByContainName("튀김");
        assertThat(fryRecipes.size()).isEqualTo(3);

        for (RecipeResponseDTO recipe: fryRecipes ) {
            System.out.println("recipe = " + recipe);
        }

        List<RecipeResponseDTO> shrimpRecipes = recipeService.findAllRecipesByContainName("새우");
        assertThat(shrimpRecipes.get(0).getName()).contains("새우");
    }

    @Test
    @DisplayName("여러 재료로 여러 레시피 검색")
    void findRecipesByMultipleIngredients(){
        //given
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").type("채소").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").type("식용유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("계란").type("유제품").build();
        IngredientDTO ingredient4 = IngredientDTO.builder().name("고추").type("채소").build();

        RecipeDTO recipe1 = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘","올리브유","고추"}).build();
        RecipeDTO recipe2 = RecipeDTO.builder().name("계란 프라이").type("전").ingredientNames(new String[]{"계란","올리브유","고추"}).build();

        //when
        ingredientService.saveIngredient(ingredient1);
        ingredientService.saveIngredient(ingredient2);
        ingredientService.saveIngredient(ingredient3);
        ingredientService.saveIngredient(ingredient4);

        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);

        //then
        // 교집합 레시피가 없는 경우
        List<RecipeResponseDTO> recipesWithMultipleIngredients = recipeService.findRecipesByMultipleIngredients(Arrays.asList(ingredient1, ingredient3));
        assertThat(recipesWithMultipleIngredients.size()).isEqualTo(0);

        //교집합 레시피가 있는 경우
        recipesWithMultipleIngredients = recipeService.findRecipesByMultipleIngredients(Arrays.asList(ingredient2, ingredient4));
        assertThat(recipesWithMultipleIngredients.size()).isEqualTo(2);

        for (RecipeResponseDTO recipe: recipesWithMultipleIngredients ) {
            System.out.println("recipe = " + recipe);
        }
    }

    @Test
    @DisplayName("레시피 저장, 재료와 연관")
    void saveRecipeWithIngredient(){
        //given
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").type("채소").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").type("식용유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("고추").type("채소").build();

        RecipeDTO recipe = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘","올리브유","고추"}).build();

        //when
        ingredientService.saveIngredient(ingredient1);
        ingredientService.saveIngredient(ingredient2);
        ingredientService.saveIngredient(ingredient3);

        recipeService.saveRecipe(recipe);
        //then
        assertThat(ingredientRepository.findByName("고추").getRecipes().get(0).getName()).isEqualTo(recipe.getName());

    }

    @Test
    @DisplayName("재료로 레시피 검색하기")
    void findRecipesByIngredient(){
        //given
        IngredientDTO ingredient = IngredientDTO.builder().name("마늘").type("채소").build();
        RecipeDTO recipe1 = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘"}).build();
        RecipeDTO recipe2 = RecipeDTO.builder().name("마늘 장아찌").type("발효 식품").ingredientNames(new String[]{"마늘"}).build();

        //when
        ingredientService.saveIngredient(ingredient);
        recipeService.saveRecipe(recipe1);
        recipeService.saveRecipe(recipe2);

        List<RecipeResponseDTO> findRecipes = recipeService.findRecipesByIngredient(ingredient);

        //then
        for (RecipeResponseDTO recipeResponseDTO: findRecipes) {
            System.out.println(recipeResponseDTO.toString());
        }
        assertThat(findRecipes.size()).isEqualTo(2);
    }

}