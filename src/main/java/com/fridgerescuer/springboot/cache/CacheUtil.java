package com.fridgerescuer.springboot.cache;

import com.fridgerescuer.springboot.data.dao.IngredientDao;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Guard;

@Slf4j
@Component
@AllArgsConstructor
public class CacheUtil {

    @Autowired
    private final CacheManager cacheManager;
/*
    public void  evictCacheFromRecipe(String cacheName, String key){
        RecipeDTO cachedRecipeDTO = cacheManager.getCache(cacheName).get(key, RecipeDTO.class);
        cachedRecipeDTO.getName();
    }*/

    public void  evictCacheFromIngredient(String cacheName, String key){
        /*
        Cache cache = cacheManager.getCache(cacheName);
        if(cache.evictIfPresent(key))
            log.info("cacheName: {}, key: {} evicted",cacheName,key );


        Cache.ValueWrapper valueWrapper = cacheManager.getCache(cacheName).get(key);
        log.info("valueWrapper: {}", valueWrapper.get());
        */

        IngredientDTO cachedIngredient = cacheManager.getCache(cacheName).get(key, IngredientDTO.class);
        log.info("Ingredient: {}", cachedIngredient.toString());
        String id = cachedIngredient.getId();
        String name = cachedIngredient.getName();

        if(cacheManager.getCache(cacheName).evictIfPresent(id))
            log.info("cacheName: {}, key: {} evicted",cacheName,id );

        if(cacheManager.getCache(cacheName).evictIfPresent(name))
            log.info("cacheName: {}, key: {} evicted",cacheName,name );
    }
/*
    @Cacheable(cacheNames = "recipe", key = "#id")//,key = "#id"
    public String callCacheMethod(String id){
        System.out.println("calling");
        RecipeDTO recipe = recipeDao.findById(id);
        return recipe.getName();
    }*/

}