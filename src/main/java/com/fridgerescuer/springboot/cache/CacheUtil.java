package com.fridgerescuer.springboot.cache;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.SimpleRecipeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class CacheUtil {

    @Autowired
    private final CacheManager cacheManager;

    public List<SimpleRecipeDTO> getAutoCompleteRecipeForSearch(String keyword){
        List<SimpleRecipeDTO> simpleRecipes = cacheManager
                .getCache(CacheType.RECIPE_AUTO_COMPLETE.getCacheName()).get(keyword, List.class);

        return simpleRecipes;
    }

    public void evictCacheFromIngredient(String cacheName, String key){
        IngredientDTO cachedIngredient = cacheManager.getCache(cacheName).get(key, IngredientDTO.class);
        if(cachedIngredient == null)
            return;

        String id = cachedIngredient.getId();
        String name = cachedIngredient.getName();

        if(cacheManager.getCache(cacheName).evictIfPresent(id))
            log.info("cacheName: {}, key: {} evicted",cacheName,id );

        if(cacheManager.getCache(cacheName).evictIfPresent(name))
            log.info("cacheName: {}, key: {} evicted",cacheName,name );
    }

    public void evictCacheByRecipeId(String key){
        final String cacheName = CacheType.RECIPE.getCacheName();
        cacheManager.getCache(cacheName).evictIfPresent(key);
    }

    public void evictCacheFromRecipeReferenceIngredient(Set<String> ingredientIds){
        if(ingredientIds == null || ingredientIds.size() == 0)
            return;

        final String ingredientCacheName = CacheType.INGREDIENT.getCacheName();
        for (String ingredientId: ingredientIds) {

            log.info("size ={} ",ingredientIds.size());

            if(cacheManager.getCache(ingredientCacheName).evictIfPresent(ingredientId))
                log.info("cacheName: {}, key: {} evicted",ingredientCacheName,ingredientId );
        }
    }

}