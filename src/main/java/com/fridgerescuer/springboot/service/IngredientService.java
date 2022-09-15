package com.fridgerescuer.springboot.service;


import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.vo.IngredientVO;

import java.time.LocalDate;

public interface IngredientService {
    ExpirationDataDTO addCustomIngredient(String memberId, IngredientDTO ingredientDTO, LocalDate date, boolean isNoExpiration);
    void addExistingIngredient(MemberDTO memberDTO, IngredientDTO ingredientDTO, LocalDate date, boolean isNoExpiration);
    IngredientVO findIngredientByName(String name);

    IngredientVO findIngredientById(String id);

    //jwt 개발 후 재구현
//    ExpirationDataDTO getExpirationData();

    //dao 개발 후 재구현
//    void deleteIngredient(String expirationDataId, String memberId);
//    ExpirationDataDTO updateIngredient(ExpirationDataDTO newData, String memberId);
//    List<IngredientVO> loadAllIngredients();

}
