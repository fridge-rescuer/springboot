package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.entity.Component;
import com.fridgerescuer.springboot.data.entity.DetailIngredient;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class IngredientDaoTest {
    @Autowired
    private IngredientRepository repository;

    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private MongoTemplate template;


    @BeforeEach
    void beforeEach(){
        repository.deleteAll();
    }

    //given
    //when
    //then
/*
    @Test
    void findByCategories(){
        //given
        DetailIngredient ingredient = DetailIngredient.builder().name("귀리_겉귀리_도정_생것").representationName("귀리").largeCategory("곡류").mediumCategory("겉귀리").
                smallCategory("도정").subCategory("생것").build();

        //when
        String savedId = repository.save(ingredient).getId();

        //then
        assertThat(repository.findAllByRepresentationName("귀리").get(0).getId()).isEqualTo(savedId);
        assertThat(repository.findAllByLargeCategory("곡류").get(0).getId()).isEqualTo(savedId);
        assertThat(repository.findAllByMediumCategory("겉귀리").get(0).getId()).isEqualTo(savedId);
        assertThat(repository.findAllBySmallCategory("도정").get(0).getId()).isEqualTo(savedId);
        assertThat(repository.findAllBySubCategory("생것").get(0).getId()).isEqualTo(savedId);

        assertTrue(repository.findAllBySubCategory("없는 카테고리").isEmpty());
    }

    @Test
    @DisplayName("저장한 재료에서 성분 가져오기기")
   void insertAndFindComponent(){
        //given
        Component component = Component.builder().kcal("12").water_g("10").build();
        DetailIngredient ingredient = DetailIngredient.builder().name("닭가슴살").component(component).build();

        //when
        Ingredient savedIngredient = repository.save(ingredient);

        //then
        assertThat(savedIngredient.getComponent().getKcal()).isEqualTo("12");
        assertThat(savedIngredient.getComponent().getKcal()).isEqualTo("12");
        assertThat(savedIngredient.getComponent().getBetaCarotene_mcg()).isNull();
    }*/
}