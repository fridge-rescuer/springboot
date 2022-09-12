package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;

public interface ExpirationDataDao {
    ExpirationData saveExpirationData(ExpirationDataDTO expirationDataDTO);

    void deleteExpirationDataById(String targetExpirationId);

    //Private

    PrivateExpirationData savePrivateExpirationData(ExpirationDataDTO expirationDataDTO);

    void deletePrivateExpirationDataById(String targetPrivateExpirationId);

}
