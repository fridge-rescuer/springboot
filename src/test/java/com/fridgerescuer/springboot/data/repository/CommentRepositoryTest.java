package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dao.CommentDao;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class CommentRepositoryTest {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private MemberRepository memberRepository;


    @BeforeEach
    void beforeEach(){
        commentRepository.deleteAll();
        recipeRepository.deleteAll();
        memberRepository.deleteAll();
    }

    //given
    //when
    //then

    @Test
    void deleteComment(){
        //given
        Recipe recipe = recipeRepository.save(Recipe.builder().name("피자").build());
        Member member = memberRepository.save(Member.builder().name("taka").build());

        Comment comment1 = Comment.builder().rating(3.5).date(LocalDate.now().toString()).recipeId(recipe.getId()).build();
        Comment comment2 = Comment.builder().rating(3.0).date(LocalDate.now().toString()).recipeId(recipe.getId()).build();

        //when
        Comment savedComment1 = commentDao.save(member.getId(), recipe.getId(), comment1);
        Comment savedComment2 = commentDao.save(member.getId(), recipe.getId(), comment2);


        //then
        commentDao.deleteComment(savedComment2.getId());
        Recipe commentRecipe = recipeRepository.findById(recipe.getId()).get();

        assertThat(commentRecipe.getComments().size()).isEqualTo(1);
        assertThat(commentRecipe.getRatingTotalSum()).isEqualTo(3.5);
        assertThat(commentRecipe.getRatingAvg()).isEqualTo(3.5);

        commentDao.deleteComment(savedComment1.getId());
        commentRecipe = recipeRepository.findById(recipe.getId()).get();

        assertThat(commentRecipe.getComments().size()).isEqualTo(0);
        assertThat(commentRecipe.getRatingTotalSum()).isEqualTo(0);
        assertThat(commentRecipe.getRatingAvg()).isEqualTo(0);

        Member commentMember = memberRepository.findById(member.getId()).get();     //후기를 남기 멤버에서도 지워졌나 확인
        assertThat(commentMember.getComments().size()).isEqualTo(0);
    }

    @Test
    void updateCommentData(){
        //given
        Recipe recipe = recipeRepository.save(Recipe.builder().name("피자").build());
        Member member = memberRepository.save(Member.builder().name("taka").build());

        Comment comment1 = Comment.builder().rating(3.5).date(LocalDate.now().toString()).recipeId(recipe.getId()).build();
        Comment comment2 = Comment.builder().rating(3.0).date(LocalDate.now().toString()).recipeId(recipe.getId()).build();
        Comment comment3 = Comment.builder().rating(4.5).date(LocalDate.now().toString()).recipeId(recipe.getId()).build();

        Comment updateComment = Comment.builder().rating(1.0).date(LocalDate.now().toString()).build();

        //when
        commentDao.save(member.getId(), recipe.getId(), comment1);
        commentDao.save(member.getId(), recipe.getId(), comment2);
        Comment targetComment = commentDao.save(member.getId(), recipe.getId(), comment3);

        Recipe foundRecipe = recipeRepository.findById(recipe.getId()).get();
        double ratingTotalSum = foundRecipe.getRatingTotalSum();

        //then
        commentDao.updateComment(targetComment.getId(), updateComment);
        Comment updatedComment = commentDao.findById(targetComment.getId());

        double expectTotalSum = ratingTotalSum + (-4.5 + 1.0);
        double expectAvg = expectTotalSum / 3;

        assertThat(updatedComment.getRating()).isEqualTo(updatedComment.getRating());   //내용 수정 확인
        assertThat(recipeRepository.findById(recipe.getId()).get().getRatingTotalSum()).isEqualTo(expectTotalSum);  //레시피의 레이핑 변동 확인
        assertThat(recipeRepository.findById(recipe.getId()).get().getRatingAvg()).isEqualTo(expectAvg);
    }

    @Test
    void saveCommentWithMemberAndRecipe(){
        //given
        Recipe recipe = recipeRepository.save(Recipe.builder().name("피자").build());
        Member member = memberRepository.save(Member.builder().name("taka").build());

        Comment comment = Comment.builder().rating(3.5).body("조금 어려웠습니다.").date(LocalDate.now().toString()).recipeId(recipe.getId()).build();

        //when
        Comment savedComment = commentDao.save(member.getId(), recipe.getId(), comment);

        //then
        Comment commentInRecipe = recipeRepository.findByName("피자").getComments().get(0);
        Comment commentInMember = memberRepository.findById(member.getId()).get().getComments().get(0);

        assertThat(commentInRecipe.getId()).isEqualTo(savedComment.getId());
        assertThat(commentInMember.getBody()).isEqualTo("조금 어려웠습니다.");
        assertThat(recipeRepository.findById(savedComment.getRecipeId()).get().getName()).isEqualTo(recipe.getName());
    }

    @Test
    @DisplayName("평점 적용 테스트")
    void checkRecipeRatingSystem(){
        //given
        Comment comment1 = Comment.builder().rating(3.5).date(LocalDate.now().toString()).build();
        Comment comment2 = Comment.builder().rating(3.0).date(LocalDate.now().toString()).build();
        Comment comment3 = Comment.builder().rating(4.5).date(LocalDate.now().toString()).build();

        //when
        Recipe recipe = recipeRepository.save(Recipe.builder().name("피자").build());
        Member member = memberRepository.save(Member.builder().name("taka").build());

        //then
        double ratingSum = 3.5;

        commentDao.save(member.getId(), recipe.getId(), comment1);
        double rating1 = recipeRepository.findByName("피자").getRatingAvg();
        assertThat(rating1).isEqualTo(ratingSum);

        commentDao.save(member.getId(), recipe.getId(), comment2);
        double rating2 = recipeRepository.findByName("피자").getRatingAvg();
        ratingSum += 3.0;
        assertThat(rating2).isEqualTo(ratingSum /2);

        commentDao.save(member.getId(), recipe.getId(), comment3);
        double rating3 = recipeRepository.findByName("피자").getRatingAvg();
        ratingSum += 4.5;
        assertThat(rating3).isEqualTo(ratingSum /3);

    }
}