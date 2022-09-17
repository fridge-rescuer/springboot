package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.ExpirationDataDao;
import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.data.vo.IngredientVO;
import com.fridgerescuer.springboot.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private final IngredientDao ingredientDao;  //언제든지 구현체를 대체가능하게 변경
    @Autowired
    private final MemberDao memberDao;
    @Autowired
    private final ExpirationDataDao expirationDataDao;

    public ExpirationDataDTO createExpirationDataList(IngredientDTO ingredient, LocalDate date, boolean isNoExpiration) {
        return ExpirationDataDTO.builder().ingredientDTO(ingredient).expirationDate(date).isNoExpiration(isNoExpiration).build();
    }

    @Override
    public ExpirationDataDTO addCustomIngredient(String memberId, IngredientDTO ingredientDTO, LocalDate date, boolean isNoExpiration) {
        ExpirationDataDTO data = createExpirationDataList(ingredientDTO, date, isNoExpiration);
        MemberDTO memberDTO = memberDao.findById(memberId);
        List<ExpirationDataDTO> list = new ArrayList<>();
        list.add(data);
        memberDao.addPrivateExpirationDataToMember(memberDTO.getId(), list);

        return data;
    }

    @Override
    public ExpirationDataDTO addExistingIngredient(String memberId, String ingredientId, LocalDate date, boolean isNoExpiration) {
        IngredientDTO ingredient = ingredientDao.findById(ingredientId);
        MemberDTO memberDTO = memberDao.findById(memberId);
        ExpirationDataDTO data = createExpirationDataList(ingredient, date, isNoExpiration);
        List<ExpirationDataDTO> list = new ArrayList<>();
        list.add(data);
        memberDao.addExpirationDataToMember(memberDTO.getId(), list);

        return data;
    }

    @Override
    public IngredientVO findIngredientByName(String name) {
        IngredientDTO foundIngredient = ingredientDao.findByName(name);
        return IngredientMapper.INSTANCE.DtoToIngredientVO(foundIngredient);
    }

    @Override
    public IngredientVO findIngredientById(String id) {
        IngredientDTO foundIngredient = ingredientDao.findById(id);

        return IngredientMapper.INSTANCE.DtoToIngredientVO(foundIngredient);
    }

    @Override
    public void deleteIngredient(String expirationDataId) {
        expirationDataDao.deletePrivateExpirationDataById(expirationDataId);
    }

    @Override
    public ExpirationDataDTO updateIngredient(ExpirationDataDTO newData) {
        expirationDataDao.updateExpirationDataById(newData.getId(), newData);

        return newData;
    }

    //jwt 개발 후 재구현
    @Override
    public Optional<ExpirationDataDTO> findExpirationData(String memberId, String ingredientName) {
        return combineExpirationData(memberId)
                .stream()
                .filter(data -> data.getIngredientDTO().getName().equals(ingredientName))
                .findFirst();
    }

    private List<ExpirationDataDTO> combineExpirationData(String memberId) {
        MemberDTO memberDTO = memberDao.findById(memberId);

        List<ExpirationDataDTO> list = new ArrayList<>();
        list.addAll(memberDTO.getExpirationDataDTOList());
        list.addAll(memberDTO.getPrivateExpirationDataDTOList());

        return list;
    }



    /**
     * 이거는 식재료 DB가 정상적인걸로 되면 다시 구현
     * load all ingredients when application starts
     */
//    @Override
//    public List<IngredientVO> loadAllIngredients() {
//        List<IngredientDTO> allIngredient = ingredientDao.loadAll();
//        return allIngredient.stream()
//                .map(ingredient -> IngredientMapper.INSTANCE.DtoToIngredientVO(ingredient))
//                .collect(Collectors.toList());
//    }

}
