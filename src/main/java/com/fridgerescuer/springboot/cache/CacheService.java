package com.fridgerescuer.springboot.cache;

import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.security.Guard;

@Slf4j
@Service
@AllArgsConstructor
public class CacheService {
    @Autowired
    private final RecipeDao recipeDao;

    @Autowired
    private final CacheManager cacheManager;
/*
    public void  evictCacheFromRecipe(String cacheName, String key){
        RecipeDTO cachedRecipeDTO = cacheManager.getCache(cacheName).get(key, RecipeDTO.class);
        cachedRecipeDTO.getName();
    }*/


    @Cacheable(cacheNames = "recipe", key = "#id")//,key = "#id"
    public String callCacheMethod(String id){
        System.out.println("calling");
        RecipeDTO recipe = recipeDao.findById(id);
        return recipe.getName();
    }

}