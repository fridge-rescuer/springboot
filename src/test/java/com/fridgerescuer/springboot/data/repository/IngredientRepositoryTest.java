package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.entity.Ingredient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class IngredientRepositoryTest {
    @Autowired
    private IngredientRepository repository;


    @BeforeEach
    void beforeEach(){
        repository.deleteAll();
    }

    //given
    //when
    //then

    @Test
    void findByCategories(){
        //given
        Ingredient ingredient = Ingredient.builder().name("귀리_겉귀리_도정_생것").representationName("귀리").largeCategory("곡류").mediumCategory("겉귀리").
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
}