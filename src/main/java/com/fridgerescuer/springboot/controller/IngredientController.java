package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.CustomExpirationForm;
import com.fridgerescuer.springboot.data.dto.ExpirationForm;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.mapper.ExpirationDataMapper;
import com.fridgerescuer.springboot.data.vo.ExpirationDataVO;
import com.fridgerescuer.springboot.data.vo.IngredientVO;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private final IngredientService ingredientService;
    @Autowired
    private final MemberService memberService;


    /**
     * jwt 토큰이 완성된 후 로그인된 사용자의 expirationdata에서 추출하는 방식으로 요청해야함
     */
//    @PostMapping("/ingredients/{expirationId}")
//    public ExpirationDataVO ingredientDetail(@PathVariable("expirationId") String expirationId) {
//        return ingredientService.(expirationId);
//    }

    /**
     *jwt 토큰이 완성된 후 로그인된 사용자의 expirationdata에 추가하는 방식으로 요청해야함. 이때 customExpirationForm 사용할 필요 없을지도?
     * 또한, addExpirationData는 DB에 있는 Ingredient를 참조하기 때문에 이에 맞는 link를 해야함
     */
//    @PostMapping("/ingredients/add")
//    public IngredientVO addExpirationData(@RequestBody CustomExpirationForm expirationForm) {
//
//    }

    /**
     *후에 jwt 토큰 추가 후 requestbody 수정예정
     */
    @PostMapping("/add")
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

    //dao 수정 후 재작성 예정
//    /**
//     *후에 jwt 토큰 추가 후 requestbody 수정예정
//     */
//    @PostMapping("/{expirationId}/edit")
//    public ExpirationDataVO editExpirationData(@RequestBody ExpirationForm data) {
//        ExpirationDataDTO expirationDataDTO = ExpirationDataDTO.builder()
//                .ingredientDTO(data.getIngredientDTO())
//                .expirationDate(data.getNewDate())
//                .isNoExpiration(data.isNoExpiration())
//                .build();
//        return ExpirationDataMapper.INSTANCE.DTOtoVO(ingredientService.updateIngredient(expirationDataDTO, data.getMemberId()));
//    }
//
//    /**
//     *후에 jwt 토큰 추가 후 requestparam 수정예정
//     */
//    @DeleteMapping("/{expirationId}/delete")
//    public void deleteExpirationData(@PathVariable("expirationId") String expirationDataId, @RequestParam("memberId") String memberId) {
//        ingredientService.deleteIngredient(expirationDataId, memberId);
//    }

}
