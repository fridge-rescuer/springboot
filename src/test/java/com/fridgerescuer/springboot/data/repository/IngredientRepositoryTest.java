package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.service.IngredientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class IngredientRepositoryTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientService ingredientService;

    @BeforeEach
    void beforeEach() {  //모두 롤백
        ingredientRepository.deleteAll();
    }

    @Test
    @DisplayName("이름으로 재료 id 찾기")
    void findIngredientIdByName(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").type("채소").build();

        IngredientResponseDTO savedIngredient = ingredientService.saveIngredient(ingredientDTO);
        IngredientResponseDTO findIngredient = ingredientService.findIngredientByName(ingredientDTO.getName());

        Assertions.assertThat(findIngredient.getId()).isEqualTo(savedIngredient.getId());
    }


    @Test
    @DisplayName("없는 재료명으로 인한 런타임 예외 처리 확인")
    void occurExceptionByNoSameName(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").type("채소").build();

        ingredientService.saveIngredient(ingredientDTO);
        Assertions.assertThatThrownBy(() ->ingredientService.findIngredientByName("양파"))
                .isInstanceOf(RuntimeException.class);


    }
}