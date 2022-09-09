package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.exception.exceptionimpl.NoSuchIngredientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    void deleteIngredient(){
        //given
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();

        //when
        IngredientDTO savedIngredient = ingredientDao.save(ingredientDTO);

        //then
        ingredientDao.deleteById(savedIngredient.getId());
        assertThatThrownBy(() -> ingredientDao.findById(savedIngredient.getId()))
                .isInstanceOf(NoSuchIngredientException.class);

        //존재하지 않는 id를 삭제하는 경우
        assertThatThrownBy(() -> ingredientDao.deleteById("1234560"))
                .isInstanceOf(NoSuchIngredientException.class);

    }

    @Test
    @DisplayName("재료의 id를 통해 재료 update")
    void updateIngredientById(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();
        IngredientDTO updateIngredientDTO = IngredientDTO.builder().name("삼겹살").build();

        IngredientDTO savedIngredient = ingredientDao.save(ingredientDTO);
        ingredientDao.updateById(savedIngredient.getId(), updateIngredientDTO);

        IngredientDTO updatedResponseDTO = ingredientDao.findById(savedIngredient.getId());

        assertThat(savedIngredient.getId()).isEqualTo(updatedResponseDTO.getId());  //update된 document가 여전히 같은 id인지 확인
        assertThat(updatedResponseDTO.getName()).isEqualTo(updateIngredientDTO.getName());

        System.out.println("updatedResponseDTO = " + updatedResponseDTO);
    }

    @Test
    @DisplayName("이름으로 재료 id 찾기")
    void findIngredientIdByName(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();

        IngredientDTO savedIngredient = ingredientDao.save(ingredientDTO);
        IngredientDTO findIngredient = ingredientDao.findByName(ingredientDTO.getName());

        assertThat(findIngredient.getId()).isEqualTo(savedIngredient.getId());
    }


    @Test
    @DisplayName("없는 재료명으로 인한 런타임 예외 처리 확인, NoSuchIngredientException")
    void occurExceptionByNoSameName(){
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("마늘").build();

        ingredientDao.save(ingredientDTO);
        assertThatThrownBy(() ->ingredientDao.findByName("양파"))
                .isInstanceOf(NoSuchIngredientException.class);
    }

    @Test
    void findIngredientContainName(){
        //given
        IngredientDTO ingredientDTO1 = IngredientDTO.builder().name("새우").build();
        IngredientDTO ingredientDTO2 = IngredientDTO.builder().name("꽃새우").build();
        IngredientDTO ingredientDTO3 = IngredientDTO.builder().name("꽃개").build();
        String targetContainName = "새우";

        //when
        ingredientDao.save(ingredientDTO1);
        ingredientDao.save(ingredientDTO2);
        ingredientDao.save(ingredientDTO3);

        //then
        List<IngredientDTO> allIngredientDTOs = ingredientDao.findAllByContainName(targetContainName);

        for (IngredientDTO ingreDTO: allIngredientDTOs ) {
            ingreDTO.getName().contains(targetContainName);
        }
    }

    /*  //db 문제로 배제
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