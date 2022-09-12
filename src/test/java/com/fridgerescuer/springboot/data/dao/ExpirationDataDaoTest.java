package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class ExpirationDataDaoTest {

    @Autowired
    private ExpirationDataDao expirationDataDao;

    @Test
    void saveExpiration(){
        ExpirationDataDTO expirationDataDTO = ExpirationDataDTO.builder().build();
        ExpirationData expirationData = expirationDataDao.saveExpirationData(expirationDataDTO);

        System.out.println(expirationData);
    }
}