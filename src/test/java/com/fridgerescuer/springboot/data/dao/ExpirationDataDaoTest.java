package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;
import com.fridgerescuer.springboot.data.repository.ExpirationDataRepository;
import com.fridgerescuer.springboot.data.repository.PrivateExpirationDataRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class ExpirationDataDaoTest {

    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private ExpirationDataDao expirationDataDao;

    @Autowired
    private ExpirationDataRepository expirationDataRepository;
    @Autowired
    private PrivateExpirationDataRepository privateExpirationDataRepository;



    @Test
    void updateExpiration(){
        ExpirationDataDTO expirationDataDTO = ExpirationDataDTO.builder().isNoExpiration(false).build();
        ExpirationData savedExpirationData = expirationDataDao.saveExpirationData(expirationDataDTO);

        IngredientDTO ingredientDTO = IngredientDTO.builder().name("목살구이").build();
        IngredientDTO savedIngredientDTO = ingredientDao.save(ingredientDTO);

        LocalDate localDate = LocalDate.now().plusDays(10);
        ExpirationDataDTO updateExpirationDataDTO = ExpirationDataDTO.builder().ingredientDTO(savedIngredientDTO).expirationDate(localDate).isNoExpiration(true).build();

        expirationDataDao.updateExpirationDataById(savedExpirationData.getId(), updateExpirationDataDTO);

        ExpirationData updatedExpirationData = expirationDataRepository.findById(savedExpirationData.getId()).get();
        Assertions.assertThat(updatedExpirationData.getIngredient().getName()).isEqualTo(savedIngredientDTO.getName());
        Assertions.assertThat(updatedExpirationData.getIngredient().getId()).isEqualTo(savedIngredientDTO.getId());
        Assertions.assertThat(updatedExpirationData.getExpirationDate()).isEqualTo(localDate);

    }

    @Test
    void updatePrivateExpiration(){
        ExpirationDataDTO expirationDataDTO = ExpirationDataDTO.builder().isNoExpiration(false).build();
        PrivateExpirationData savedExpirationData = expirationDataDao.savePrivateExpirationData(expirationDataDTO);

        IngredientDTO ingredientDTO = IngredientDTO.builder().name("목살구이").build();

        LocalDate localDate = LocalDate.now().plusDays(10);
        ExpirationDataDTO updateExpirationDataDTO = ExpirationDataDTO.builder().ingredientDTO(ingredientDTO).expirationDate(localDate).isNoExpiration(true).build();

        expirationDataDao.updatePrivateExpirationDataById(savedExpirationData.getId(), updateExpirationDataDTO);

        PrivateExpirationData updatedExpirationData = privateExpirationDataRepository.findById(savedExpirationData.getId()).get();
        Assertions.assertThat(updatedExpirationData.getIngredient().getName()).isEqualTo(ingredientDTO.getName());
        Assertions.assertThat(updatedExpirationData.getExpirationDate()).isEqualTo(localDate);
        Assertions.assertThat(updatedExpirationData.getIsNoExpiration()).isEqualTo(true);

    }
}