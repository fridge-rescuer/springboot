package com.fridgerescuer.springboot.service.impl;

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

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private final IngredientDao ingredientDao;  //언제든지 구현체를 대체가능하게 변경
    @Autowired
    private final MemberDao memberDao;

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
    public void addExistingIngredient(MemberDTO memberDTO, IngredientDTO ingredientDTO, LocalDate date, boolean isNoExpiration) {
        ExpirationDataDTO data = createExpirationDataList(ingredientDTO, date, isNoExpiration);
        List<ExpirationDataDTO> list = new ArrayList<>();
        list.add(data);
        memberDao.addExpirationDataToMember(memberDTO.getId(), list);
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

    //dao 개발 후 재구현
//    @Override
//    public void deleteIngredient(String expirationDataId, String memberId) {
//        MemberDTO member = memberDao.findById(memberId);
//        ExpirationDataDTO expirationDataDTO = member.getExpirationDataDTOList().stream().filter(data -> data.getExpirationId().equals(expirationDataId));
//        memberDao.deleteExpirationData(expirationDataDTO, member);
//    }

    //dao 개발 후 재구현
//    @Override
//    public ExpirationDataDTO updateIngredient(ExpirationDataDTO newData, String memberId) {
//    }

    //jwt 개발 후 재구현
//    public ExpirationDataDTO getExpirationData() {}

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
