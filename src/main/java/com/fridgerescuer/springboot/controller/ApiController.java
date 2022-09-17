package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.vo.IngredientVO;
import com.fridgerescuer.springboot.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private final IngredientService ingredientService;

    //db 문제 해결 후 가동
//    @PostMapping("/loadIngredients")
//    public List<IngredientVO> loadAllIngredients() {
//        return ingredientService.loadAllIngredients();
//    }
}
