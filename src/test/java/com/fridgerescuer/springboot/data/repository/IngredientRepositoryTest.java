package com.fridgerescuer.springboot.data.repository;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchIngredientException;
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

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    //given
    //when
    //then

    @Test
    void deleteIngredient(){
        //given
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();

        //when
        IngredientResponseDTO savedIngredient = ingredientService.saveIngredient(ingredientDTO);

        //then
        ingredientService.deleteIngredient(savedIngredient.getId());
        assertThatThrownBy(() -> ingredientService.findIngredientById(savedIngredient.getId()))
                .isInstanceOf(NoSuchIngredientException.class);

        //존재하지 않는 id를 삭제하는 경우
        assertThatThrownBy(() -> ingredientService.deleteIngredient("1234560"))
                .isInstanceOf(NoSuchIngredientException.class);

    }

    @Test
    @DisplayName("재료의 id를 통해 재료 update")
    void updateIngredientById(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();
        IngredientDTO updateIngredientDTO = IngredientDTO.builder().name("삼겹살").build();

        IngredientResponseDTO savedIngredient = ingredientService.saveIngredient(ingredientDTO);
        ingredientService.updateIngredient(savedIngredient.getId(), updateIngredientDTO);

        IngredientResponseDTO updatedResponseDTO = ingredientService.findIngredientById(savedIngredient.getId());

        assertThat(savedIngredient.getId()).isEqualTo(updatedResponseDTO.getId());  //update된 document가 여전히 같은 id인지 확인
        assertThat(updatedResponseDTO.getName()).isEqualTo(updateIngredientDTO.getName());

        System.out.println("updatedResponseDTO = " + updatedResponseDTO);
    }

    @Test
    @DisplayName("이름으로 재료 id 찾기")
    void findIngredientIdByName(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();

        IngredientResponseDTO savedIngredient = ingredientService.saveIngredient(ingredientDTO);
        IngredientResponseDTO findIngredient = ingredientService.findIngredientByName(ingredientDTO.getName());

        assertThat(findIngredient.getId()).isEqualTo(savedIngredient.getId());
    }


    @Test
    @DisplayName("없는 재료명으로 인한 런타임 예외 처리 확인, NoSuchIngredientException")
    void occurExceptionByNoSameName(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();

        ingredientService.saveIngredient(ingredientDTO);
        assertThatThrownBy(() ->ingredientService.findIngredientByName("양파"))
                .isInstanceOf(NoSuchIngredientException.class);
    }
}