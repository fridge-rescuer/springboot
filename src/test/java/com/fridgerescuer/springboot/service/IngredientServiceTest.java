package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dao.ExpirationDataDao;
import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import com.fridgerescuer.springboot.data.vo.ExpirationDataVO;
import com.fridgerescuer.springboot.data.vo.IngredientVO;
import com.fridgerescuer.springboot.exception.errorcodeimpl.IngredientError;
import com.fridgerescuer.springboot.exception.exceptionimpl.IngredientException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
public class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ExpirationDataDao expirationDataDao;
    @Autowired
    private IngredientDao ingredientDao;

    @BeforeEach
    void beforeEach(){
        MemberDTO testMember = MemberDTO.builder().id("rescue123").name("홍길동").password("abcd1234").build();
        memberService.memberJoin(testMember);

        IngredientDTO ingredientDTO = IngredientDTO.builder().name("김치찌개").build();
        IngredientDTO savedIngredient = ingredientDao.save(ingredientDTO);
    }

    @Test
    void addCustomIngredientTest() {
        LocalDate expirationDate = LocalDate.of(2022, 9, 15);
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("김치찌개").build();
        IngredientVO ingredient = IngredientMapper.INSTANCE.DtoToIngredientVO(ingredientDTO);

        ingredientService.addCustomIngredient("rescue123", ingredientDTO, expirationDate, false);
        ExpirationDataDTO trySaveData = ingredientService.findExpirationData("rescue123", "김치찌개")
                .orElseThrow(() -> new IngredientException(IngredientError.NOT_EXIST));

        Assertions.assertThat(trySaveData.getExpirationDate()).isEqualTo(expirationDate);
        Assertions.assertThat(IngredientMapper.INSTANCE.DtoToIngredientVO(trySaveData.getIngredientDTO())).isEqualTo(ingredient);
        Assertions.assertThat(trySaveData.getIsNoExpiration()).isFalse();

    }

    @Test
    void addExistIngredientTest() {
        IngredientVO ingredient = ingredientService.findIngredientByName("김치찌개");
        LocalDate expirationDate = LocalDate.of(2022, 9, 15);


        ingredientService.addExistingIngredient("rescue123", ingredient.getId(), expirationDate, false);
        ExpirationDataDTO trySaveData = ingredientService.findExpirationData("rescue123", "김치찌개")
                .orElseThrow(() -> new IngredientException(IngredientError.NOT_EXIST));

        Assertions.assertThat(trySaveData.getExpirationDate()).isEqualTo(expirationDate);
        Assertions.assertThat(IngredientMapper.INSTANCE.DtoToIngredientVO(trySaveData.getIngredientDTO())).isEqualTo(ingredient);
        Assertions.assertThat(trySaveData.getIsNoExpiration()).isFalse();

    }

    @Test
    void findIngredientTest() {
        ingredientService.findIngredientByName("김치찌개");
    }

}
