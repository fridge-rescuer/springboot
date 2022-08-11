package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.IngredientDAO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component  //빈으로 등록되어 다른 클래스가 인터페이스로 의존성 주입 받을 때 자동 등록됨
@RequiredArgsConstructor
public class IngredientDAOImpl implements IngredientDAO {

    @Autowired
    private final IngredientRepository repository;

    @Override
    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @Override
    public Ingredient find(Ingredient ingredient) { //이름으로 찾기
        return repository.findByName(ingredient.getName());
    }

    @Override
    public Ingredient findByName(String name) {
        Ingredient findIngredient = repository.findByName(name);
        if(findIngredient ==null){
            throw new RuntimeException("no ingredient");
        }

        return repository.findByName(name);
    }
}
