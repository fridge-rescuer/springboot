package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;

public interface ExpirationDataDao {
    ExpirationData saveExpirationData(ExpirationDataDTO expirationDataDTO);
    void updateExpirationDataById(String targetExpirationId, ExpirationDataDTO updateDataDTO);
    void deleteExpirationDataById(String targetExpirationId);

    //PrivateExpirationData methods

    PrivateExpirationData savePrivateExpirationData(ExpirationDataDTO expirationDataDTO);
    void updatePrivateExpirationDataById(String targetPrivateExpirationId, ExpirationDataDTO updateDataDTO);
    void deletePrivateExpirationDataById(String targetPrivateExpirationId);

}
