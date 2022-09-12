package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;

public interface ExpirationDataDao {
    ExpirationData saveExpirationData(ExpirationDataDTO expirationDataDTO);

    void deleteExpirationDataById(String targetExpirationId);
}
