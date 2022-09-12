package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.ExpirationDataDao;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;
import com.fridgerescuer.springboot.data.mapper.ExpirationDataMapper;
import com.fridgerescuer.springboot.data.repository.ExpirationDataRepository;
import com.fridgerescuer.springboot.data.repository.PrivateExpirationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ExpirationDataDaoImpl implements ExpirationDataDao {

    @Autowired
    private final MongoTemplate template;

    @Autowired
    private final ExpirationDataRepository expirationRepository;
    @Autowired
    private final PrivateExpirationDataRepository privateExpirationRepository;

    private final ExpirationDataMapper expirationDataMapper = ExpirationDataMapper.INSTANCE;

    private void checkExistExpirationId(String id){
        Optional<ExpirationData> foundData = expirationRepository.findById(id);

        if(foundData.isEmpty()){
            // 예외
        }
    }

    /*
     * @implSpec
     * return으로 ExpirationData Entity를 넘기므로
     * memberDao와 같은 Data layer에서만 호출을 권장
     * */
    
    @Override
    public ExpirationData saveExpirationData(ExpirationDataDTO expirationDataDTO) {
        ExpirationData savedData = expirationRepository.save(expirationDataMapper.DTOtoData(expirationDataDTO));

        log.info("save ExpirationData ={}", savedData);
        return savedData;
    }

    @Override
    public void deleteExpirationDataById(String targetExpirationId){
        checkExistExpirationId(targetExpirationId);

        expirationRepository.deleteById(targetExpirationId);
        log.info("delete ExpirationData id ={}", targetExpirationId);
    }

    /*
    * @implSpec
    * return으로 PrivateExpirationData Entity를 넘기므로
    * memberDao와 같은 Data layer에서만 호출을 권장
    * */

    @Override
    public PrivateExpirationData savePrivateExpirationData(ExpirationDataDTO expirationDataDTO) {
        PrivateExpirationData savedPrivateData = privateExpirationRepository.save(expirationDataMapper.DTOtoPrivateData(expirationDataDTO));

        log.info("save ExpirationData ={}", savedPrivateData);
        return savedPrivateData;
    }

    @Override
    public void deletePrivateExpirationDataById(String targetPrivateExpirationId) {
        //checkExistExpirationId(targetExpirationId);

        privateExpirationRepository.deleteById(targetPrivateExpirationId);
        log.info("delete PrivateExpirationData id ={}", targetPrivateExpirationId);
    }
}
