package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.CustomExpirationForm;
import com.fridgerescuer.springboot.data.dto.ExpirationForm;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.mapper.ExpirationDataMapper;
import com.fridgerescuer.springboot.data.vo.ExpirationDataVO;
import com.fridgerescuer.springboot.exception.errorcodeimpl.IngredientError;
import com.fridgerescuer.springboot.exception.exceptionimpl.IngredientException;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/ingredients")
public class IngredientController {

    @Autowired
    private final IngredientService ingredientService;
    @Autowired
    private final MemberService memberService;



    @PostMapping("/{expirationId}")
    public ExpirationDataVO ExpirationDataDetail(@PathVariable("expirationId") String expirationId, String memberId) {
        return ExpirationDataMapper.INSTANCE.DTOtoVO(
                        ingredientService.findExpirationData(memberId, expirationId)
                                .orElseThrow(() -> new IngredientException(IngredientError.NOT_EXIST))
                );
    }

    @PostMapping("/add")
    public ExpirationDataVO addExpirationData(@RequestBody ExpirationForm expirationForm) {
        return ExpirationDataMapper.INSTANCE.DTOtoVO(
            ingredientService.addExistingIngredient(
                    expirationForm.getMemberId(),
                    expirationForm.getIngredientDTO().getId(),
                    expirationForm.getNewDate(),
                    expirationForm.isNoExpiration())
        );
    }

    /**
     *후에 jwt 토큰 추가 후 requestbody 수정예정
     */
    @PostMapping("/add/customIngredient")
    public ExpirationDataVO addPrivateExpirationData(@RequestBody CustomExpirationForm expirationForm) {
        IngredientDTO ingredientDTO = IngredientDTO.builder().name(expirationForm.getIngredientName()).build();
        return ExpirationDataMapper.INSTANCE.DTOtoVO(
                ingredientService.addCustomIngredient(
                        expirationForm.getMemberId(),
                        ingredientDTO,
                        expirationForm.getNewDate(),
                        expirationForm.isNoExpiration()
                )
        );
    }

    /**
     *후에 jwt 토큰 추가 후 requestbody 수정예정
     */
    @PostMapping("/{expirationId}/edit")
    public ExpirationDataVO editExpirationData(@RequestBody ExpirationForm data) {
        ExpirationDataDTO expirationDataDTO = ExpirationDataDTO.builder()
                .ingredientDTO(data.getIngredientDTO())
                .expirationDate(data.getNewDate())
                .isNoExpiration(data.isNoExpiration())
                .build();
        return ExpirationDataMapper.INSTANCE.DTOtoVO(ingredientService.updateIngredient(expirationDataDTO));
    }

    /**
     *후에 jwt 토큰 추가 후 requestparam 수정예정
     */
    @DeleteMapping("/{expirationId}/delete")
    public void deleteExpirationData(@PathVariable("expirationId") String expirationDataId) {
        ingredientService.deleteIngredient(expirationDataId);
    }

}
