package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/recipe")
public class RecipeController { //사진 등록 테스트용 컨트롤러

    @Autowired
    private final RecipeService recipeService;

    @PostMapping("/upload")
    public String addImage(@RequestParam("name") String name, @RequestParam("image")MultipartFile image, Model model) throws IOException {
        RecipeDTO recipe = RecipeDTO.builder().name(name).build();
        String savedId = recipeService.saveRecipe(recipe).getId();
        recipeService.addRecipeImage(savedId, image);

        RecipeResponseDTO foundRecipe = recipeService.findById(savedId);
        log.info("saved = {}", foundRecipe);
        return "redirect:/recipe/" + savedId;
    }

    @GetMapping("/{id}")
    public String getImage(@PathVariable String id, Model model){
        RecipeResponseDTO foundRecipe = recipeService.findById(id);
        model.addAttribute("name", foundRecipe.getName());
        model.addAttribute("image", Base64.getEncoder().encodeToString(foundRecipe.getImage().getData()));
        return "recipe";
    }


}
