package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.IngredientResponseDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.mapper.IngredientMapper;
import com.fridgerescuer.springboot.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private final IngredientDao ingredientDao;  //언제든지 구현체를 대체가능하게 변경

    @Override
    public IngredientResponseDTO saveIngredient(IngredientDTO ingredientDTO){

        Ingredient savedIngredient = ingredientDao.save(IngredientMapper.INSTANCE.DTOtoIngredient(ingredientDTO));

        return IngredientMapper.INSTANCE.ingredientToResponseDTO(savedIngredient);
    }

    @Override
    public IngredientResponseDTO findIngredientByName(String name) {
        Ingredient foundIngredient = ingredientDao.findByName(name);
        return IngredientMapper.INSTANCE.ingredientToResponseDTO(foundIngredient);
    }

    @Override
    public IngredientResponseDTO findIngredientById(String id) {
        Ingredient foundIngredient = ingredientDao.findById(id);

        return IngredientMapper.INSTANCE.ingredientToResponseDTO(foundIngredient);
    }

    @Override
    public void updateIngredient(String id,IngredientDTO ingredientDTO) {
        ingredientDao.update(id, IngredientMapper.INSTANCE.DTOtoIngredient(ingredientDTO));
    }

    @Override
    public void deleteIngredient(String id) {
        ingredientDao.delete(id);
    }
}
