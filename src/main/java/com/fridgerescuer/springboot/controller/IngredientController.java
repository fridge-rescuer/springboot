package com.fridgerescuer.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ingredient")
public class IngredientController {
/*
    @Autowired
    private final IngredientService ingredientService;

    @ApiOperation(value = "재료 생성 테스트", notes = "@RequestBody를 통한 POST 방식")
    @PostMapping()
    public ResponseEntity<IngredientResponseDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO){
        IngredientResponseDTO ingredientResponseDTO = ingredientService.saveIngredient(ingredientDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDTO);
    }
    */
}
