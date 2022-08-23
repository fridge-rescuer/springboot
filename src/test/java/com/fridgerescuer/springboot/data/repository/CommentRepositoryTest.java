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
    void saveCommentWithMemberAndRecipe(){
        //given
        Comment comment = Comment.builder().rating(3.5).body("조금 어려웠습니다.").date(LocalDate.now().toString()).build();

        //when
        Recipe recipe = recipeRepository.save(Recipe.builder().name("피자").build());
        Member member = memberRepository.save(Member.builder().name("taka").build());

        Comment savedComment = commentDao.save(member.getId(), recipe.getId(), comment);

        //then
        Comment commentInRecipe = recipeRepository.findByName("피자").getComments().get(0);
        Comment commentInMember = memberRepository.findById(member.getId()).get().getComments().get(0);

        assertThat(commentInRecipe.getId()).isEqualTo(savedComment.getId());
        assertThat(commentInMember.getBody()).isEqualTo("조금 어려웠습니다.");
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