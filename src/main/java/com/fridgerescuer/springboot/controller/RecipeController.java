package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/recipe")
public class RecipeController { //사진 등록 테스트용 컨트롤러, 테스트용이라 전부 삭제해도됨
/*
    @Autowired
    private final RecipeService recipeService;
    @Autowired
    private final RecipeRepository repository; //테스트 용임

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations operations;

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

    @GetMapping("/{id}")
    public String getImage(@PathVariable String id, Model model) {
        Recipe foundRecipe = repository.findById(id).get();
        log.info("found recipe ={}", foundRecipe);

        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(foundRecipe.getImageId())));
        GridFsResource resource = operations.getResource(file);


        model.addAttribute("name", foundRecipe.getName());
        try {
            model.addAttribute("image",resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "recipe";
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] testphoto(@PathVariable String id) throws IOException {Recipe foundRecipe = repository.findById(id).get();
        log.info("found recipe ={}", foundRecipe);

        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(foundRecipe.getImageId())));
        GridFsResource resource = operations.getResource(file);

        InputStream in = resource.getInputStream();
        return in.readAllBytes();
    }
    */
}
