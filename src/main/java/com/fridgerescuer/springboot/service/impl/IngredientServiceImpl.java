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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private final IngredientDao ingredientDao;  //언제든지 구현체를 대체가능하게 변경

    @Override
    public void saveCustomIngredient(IngredientDTO ingredientDTO){
        ingredientDao.save(IngredientMapper.INSTANCE.DTOtoIngredient(ingredientDTO));
    }

    @Override
    public void saveIngredient(IngredientDTO ingredientDTO) {

    }

    @Override
    public IngredientResponseDTO findIngredientByName(String name) {
        Ingredient foundIngredient = ingredientDao.findByName(name);
        return IngredientMapper.INSTANCE.ingredientToResponseDTO(foundIngredient);
    }

    /**
     * load all ingredients when application starts
     * @return Set<IngredientResponseDTO> set
     */
    @Override
    public Set<IngredientResponseDTO> loadAllIngredients() {
        Set<Ingredient> allIngredient = ingredientDao.loadAll();
        return allIngredient.stream()
                .map(ingredient -> IngredientMapper.INSTANCE.ingredientToResponseDTO(ingredient))
                .collect(Collectors.toSet());
    }

    @Override
    public IngredientResponseDTO findIngredientById(String id) {
        Ingredient foundIngredient = ingredientDao.findById(id);

        return IngredientMapper.INSTANCE.ingredientToResponseDTO(foundIngredient);
    }

}
