package com.fridgerescuer.springboot.service;


import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.vo.IngredientVO;

import java.time.LocalDate;
import java.util.Optional;

public interface IngredientService {
    ExpirationDataDTO addCustomIngredient(String memberId, IngredientDTO ingredientDTO, LocalDate date, boolean isNoExpiration);
    ExpirationDataDTO addExistingIngredient(String memberId, String ingredientId, LocalDate date, boolean isNoExpiration);

    IngredientVO findIngredientByName(String name);

    IngredientVO findIngredientById(String id);

    void deleteIngredient(String expirationDataId);
    ExpirationDataDTO updateIngredient(ExpirationDataDTO newData);

    Optional<ExpirationDataDTO> findExpirationData(String memberId, String ingredientName);


    //dao 개발 후 재구현
//    List<IngredientVO> loadAllIngredients();

}
