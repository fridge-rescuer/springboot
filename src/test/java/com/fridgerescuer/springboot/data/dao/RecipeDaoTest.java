package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.fridgerescuer.springboot.exception.exceptionimpl.RecipeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class RecipeDaoTest {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;


    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private MemberDao memberDao;


    @BeforeEach
    void beforeEach() {  //모두 롤백
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    //given
    //when
    //then

    @Test
    void deleteRecipeById(){
        //given
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("고추").build();

        RecipeDTO recipe = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘","올리브유","고추"}).build();

        //when
        ingredientDao.save(ingredient1);
        ingredientDao.save(ingredient2);
        ingredientDao.save(ingredient3);

        RecipeDTO recipeResponseDTO = recipeDao.save(recipe);
        assertThat(ingredientRepository.findByName("마늘").getRecipes().size()).isEqualTo(1);

        //then
        recipeDao.deleteById(recipeResponseDTO.getId());
        assertThatThrownBy(() -> recipeDao.findById(recipeResponseDTO.getId()))
                .isInstanceOf(RecipeException.class);

        //연관 관계 제거도 확인
        assertThat(ingredientRepository.findByName("마늘").getRecipes().size()).isEqualTo(0);

    }

    @Test
    void recipeUpdateAndDelete(){
        //given
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("고추").build();
        IngredientDTO ingredient4 = IngredientDTO.builder().name("감자").build();

        RecipeDTO recipe = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘","올리브유","고추"}).build();
        RecipeDTO updateDateRecipe = RecipeDTO.builder().name("감자 튀김").type("튀김").ingredientNames(new String[]{"감자"}).build();

        //when
        ingredientDao.save(ingredient1);
        ingredientDao.save(ingredient2);
        ingredientDao.save(ingredient3);
        ingredientDao.save(ingredient4);

        RecipeDTO savedRecipe = recipeDao.save(recipe);
        recipeDao.updateRecipeById(savedRecipe.getId(), updateDateRecipe);

        //then
        RecipeDTO updatedRecipe = recipeDao.findById(savedRecipe.getId());
        assertThat(updatedRecipe.getName()).isEqualTo("감자 튀김");     // 내용 update 확인
        assertThat(ingredientRepository.findByName("마늘").getRecipes().size()).isEqualTo(0); //연관관계 제거 확인
        assertThat(ingredientRepository.findByName("감자").getRecipes().size()).isEqualTo(1); // 연관관계 재 생성 확인

        System.out.println("updatedRecipe = " + updatedRecipe);
    }

    @Test
    @DisplayName("해당 이름을 포함하는 모든 레시피 찾기")
    void findAllRecipesByContainName(){
        //given
        RecipeDTO recipe1 = RecipeDTO.builder().name("감자 튀김").type("튀김").ingredientNames(new String[]{}).build();
        RecipeDTO recipe2 = RecipeDTO.builder().name("새우튀김").type("튀김").ingredientNames(new String[]{}).build();
        RecipeDTO recipe3 = RecipeDTO.builder().name("병아리 튀김").type("튀김").ingredientNames(new String[]{}).build();

        //when
        recipeDao.save(recipe1);
        recipeDao.save(recipe2);
        recipeDao.save(recipe3);

        //then
        List<RecipeDTO> fryRecipes = recipeDao.findAllByContainName("튀김");
        assertThat(fryRecipes.size()).isEqualTo(3);

        for (RecipeDTO recipe: fryRecipes ) {
            System.out.println("recipe = " + recipe);
        }

        List<RecipeDTO> shrimpRecipes = recipeDao.findAllByContainName("새우");
        assertThat(shrimpRecipes.get(0).getName()).contains("새우");
    }
/*
    @Test
    @DisplayName("여러 재료로 여러 레시피 검색")
    void findRecipesByMultipleIngredients(){
        //given
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("계란").build();
        IngredientDTO ingredient4 = IngredientDTO.builder().name("고추").build();

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
*/
    @Test
    @DisplayName("레시피 저장, 재료와 연관")
    void saveRecipeWithIngredient(){
        //given
        IngredientDTO ingredient1 = IngredientDTO.builder().name("마늘").build();
        IngredientDTO ingredient2 = IngredientDTO.builder().name("올리브유").build();
        IngredientDTO ingredient3 = IngredientDTO.builder().name("고추").build();

        RecipeDTO recipe = RecipeDTO.builder().name("알리오 올리오").type("파스타").ingredientNames(new String[]{"마늘","올리브유","고추"}).build();

        //when
        ingredientDao.save(ingredient1);
        ingredientDao.save(ingredient2);
        ingredientDao.save(ingredient3);

        recipeDao.save(recipe);
        //then
        assertThat(ingredientRepository.findByName("고추").getRecipes().get(0).getName()).isEqualTo(recipe.getName());
    }

    @Test
    @DisplayName("레시피 후기들 레시피 id로 받아오기")
    void getRecipeCommentsByRecipeId(){
        //given
        MemberDTO member = MemberDTO.builder().name("아사쿠사").build();
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
        List<CommentDTO> comments = recipeDao.getCommentsByRecipeId(recipeResponseDTO.getId());

        for (int i = 0; i < comments.size(); i++) {
            assertThat(comments.get(i).getId()).isEqualTo(commentResponseDTOs.get(i).getId());
            assertThat(comments.get(i).getRating()).isEqualTo(commentResponseDTOs.get(i).getRating());
        }
    }
}
