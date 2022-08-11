package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.dto.RecipeResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.mapper.RecipeMapper;
import com.fridgerescuer.springboot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @Autowired private final RecipeDao recipeDao;
    @Autowired private final IngredientDao ingredientDao;
    @Autowired private final MemberDao memberDao;

    @Override
    public RecipeResponseDTO saveRecipe(RecipeDTO recipeDTO) {
        Recipe savedRecipe = recipeDao.save(RecipeMapper.INSTANCE.DTOtoRecipe(recipeDTO));

        return RecipeMapper.INSTANCE.recipeToResponseDTO(savedRecipe);
    }

    @Override
    public RecipeResponseDTO saveRecipeByMember(String memberId, RecipeDTO recipeDTO) {
        RecipeResponseDTO recipeResponseDTO = this.saveRecipe(recipeDTO);

        memberDao.addRecipeToMember(memberId, RecipeMapper.INSTANCE.responseDTOtoRecipe(recipeResponseDTO));
        return recipeResponseDTO;
    }

    @Override
    public List<RecipeResponseDTO> findAllRecipesByContainName(String name) {
        List<Recipe> recipes = recipeDao.findAllByContainName(name);

        List<RecipeResponseDTO> results = new ArrayList<>();
        for (Recipe recipe: recipes ) {
            results.add(RecipeMapper.INSTANCE.recipeToResponseDTO(recipe));
        }

        return results;
    }

    @Override
    public List<RecipeResponseDTO> findRecipesByIngredient(IngredientDTO ingredientDTO) {
        Ingredient findIngredient = ingredientDao.find(Ingredient.builder()
                .name(ingredientDTO.getName())
                .type(ingredientDTO.getType()).build());

        List<RecipeResponseDTO> results = new ArrayList<>();

        for(Recipe recipe :findIngredient.getRecipes()){
            results.add(RecipeMapper.INSTANCE.recipeToResponseDTO(recipe));
        }

        return results;
    }

    @Override
    public List<RecipeResponseDTO> findRecipesByMultipleIngredients(List<IngredientDTO> ingredientDTOList) {
        final int FIRST_IDX = 0;

        if(ingredientDTOList.size() ==1)   //여러 재료가 아니라면 바로 반환
            return findRecipesByIngredient(ingredientDTOList.get(FIRST_IDX));

        Map<String,RecipeResponseDTO> intersectionRecipeResponses = new HashMap<>();    //교집합 레시피
        for (RecipeResponseDTO recipeResponseDTO: findRecipesByIngredient(ingredientDTOList.get(FIRST_IDX)) ) {
            intersectionRecipeResponses.put(recipeResponseDTO.getId(), recipeResponseDTO);
        }
        
        for(int i=1; i<ingredientDTOList.size() ; ++i){
            List<RecipeResponseDTO> recipes = findRecipesByIngredient(ingredientDTOList.get(i));

            Map<String,RecipeResponseDTO> aliveRecipes = new HashMap<>();
            for (RecipeResponseDTO recipeResponseDTO : recipes){
                if(intersectionRecipeResponses.containsKey(recipeResponseDTO.getId())){
                    aliveRecipes.put(recipeResponseDTO.getId(), recipeResponseDTO);
                }
            }

            intersectionRecipeResponses = aliveRecipes; // 교집합이 남은 레시피만 남기고 소거
        }

        List<RecipeResponseDTO> results = intersectionRecipeResponses.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));

        return results;
    }



}
