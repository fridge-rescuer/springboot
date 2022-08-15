package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.service.IngredientService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private final IngredientService ingredientService;

    @ApiOperation(value = "재료 생성 테스트", notes = "@RequestBody를 통한 POST 방식")
    @PostMapping()
    public ResponseEntity<IngredientResponseDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO){
        IngredientResponseDTO ingredientResponseDTO = ingredientService.saveIngredient(ingredientDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDTO);
    }
}
