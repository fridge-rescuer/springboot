package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.ExpirationDataDao;
import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;
import com.fridgerescuer.springboot.data.mapper.ExpirationDataMapper;
import com.fridgerescuer.springboot.data.repository.ExpirationDataRepository;
import com.fridgerescuer.springboot.data.repository.PrivateExpirationDataRepository;
import com.fridgerescuer.springboot.exception.exceptionimpl.NoSuchExpirationDataException;
import com.fridgerescuer.springboot.exception.exceptionimpl.NoSuchPrivateExpirationDataException;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    private final IngredientDao ingredientDao;

    @Autowired
    private final ExpirationDataRepository expirationRepository;
    @Autowired
    private final PrivateExpirationDataRepository privateExpirationRepository;

    private final ExpirationDataMapper expirationDataMapper = ExpirationDataMapper.INSTANCE;

    private void checkExistExpirationId(String id){
        Optional<ExpirationData> foundData = expirationRepository.findById(id);

        if(foundData.isEmpty()){
            throw new NoSuchExpirationDataException(new NullPointerException("no such ExpirationData id =" + id));
        }
    }

    private ExpirationData getExpirationDataById(String id){
        Optional<ExpirationData> foundData = expirationRepository.findById(id);

        if(foundData.isEmpty()){
            throw new NoSuchExpirationDataException(new NullPointerException("no such ExpirationData id =" + id));
        }

        return foundData.get();
    }

    private void checkPrivateExistExpirationId(String id){
        Optional<PrivateExpirationData> foundData = privateExpirationRepository.findById(id);

        if(foundData.isEmpty()){
            throw new NoSuchPrivateExpirationDataException(new NullPointerException("no such PrivateExpirationData id =" + id));
        }
    }

    private PrivateExpirationData getPrivateExpirationDataById(String id){
        Optional<PrivateExpirationData> foundData = privateExpirationRepository.findById(id);

        if(foundData.isEmpty()){
            throw new NoSuchPrivateExpirationDataException(new NullPointerException("no such PrivateExpirationData id =" + id));
        }

        return foundData.get();
    }

    /*
     * @implSpec
     * return?????? ExpirationData Entity??? ????????????
     * memberDao??? ?????? Data layer????????? ????????? ??????
     * */

    @Override
    public ExpirationData saveExpirationData(ExpirationDataDTO expirationDataDTO) {
        ExpirationData savedData = expirationRepository.save(expirationDataMapper.DTOtoData(expirationDataDTO));

        log.info("save ExpirationData ={}", savedData);
        return savedData;
    }

    @Override
    public void updateExpirationDataById(String targetExpirationId, ExpirationDataDTO updateDataDTO) {
        ExpirationData expirationData = expirationDataMapper.DTOtoData(updateDataDTO);
        ingredientDao.checkExistingIngredientId(expirationData.getIngredient().getId());    //?????? ?????? ?????????

        checkExistExpirationId(targetExpirationId); // ?????? id??? ??????????????? ??????

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetExpirationId));

        Update update = new Update();
        update.set("ingredient",expirationData.getIngredient());
        update.set("expirationDate",expirationData.getExpirationDate());
        update.set("isNoExpiration",expirationData.getIsNoExpiration());

        template.updateMulti(query, update, ExpirationData.class);

        log.info("update expirationData ={}", expirationData);
    }

    @Override
    public void deleteExpirationDataById(String targetExpirationId){
        checkExistExpirationId(targetExpirationId);

        expirationRepository.deleteById(targetExpirationId);
        log.info("delete ExpirationData id ={}", targetExpirationId);
    }

    /*
    * @implSpec
    * return?????? PrivateExpirationData Entity??? ????????????
    * memberDao??? ?????? Data layer????????? ????????? ??????
    * */

    @Override
    public PrivateExpirationData savePrivateExpirationData(ExpirationDataDTO expirationDataDTO) {
        PrivateExpirationData savedPrivateData = privateExpirationRepository.save(expirationDataMapper.DTOtoPrivateData(expirationDataDTO));

        log.info("save ExpirationData ={}", savedPrivateData);
        return savedPrivateData;
    }

    @Override
    public void updatePrivateExpirationDataById(String targetPrivateExpirationId, ExpirationDataDTO updateDataDTO) {
        PrivateExpirationData privateExpirationData = expirationDataMapper.DTOtoPrivateData(updateDataDTO);
        //private??? ????????? ????????? ???????????? ???????????? id ?????? x

        checkPrivateExistExpirationId(targetPrivateExpirationId);   //?????? id?????? ??????

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetPrivateExpirationId));

        Update update = new Update();
        update.set("ingredient",privateExpirationData.getIngredient());
        update.set("expirationDate",privateExpirationData.getExpirationDate());
        update.set("isNoExpiration",privateExpirationData.getIsNoExpiration());

        template.updateMulti(query, update, PrivateExpirationData.class);

        log.info("update privateExpirationData ={}", privateExpirationData);
    }

    @Override
    public void deletePrivateExpirationDataById(String targetPrivateExpirationId) {
        checkPrivateExistExpirationId(targetPrivateExpirationId);

        privateExpirationRepository.deleteById(targetPrivateExpirationId);
        log.info("delete PrivateExpirationData id ={}", targetPrivateExpirationId);
    }
}
