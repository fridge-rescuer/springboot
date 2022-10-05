package com.fridgerescuer.springboot.cache;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CacheUtil {

    @Autowired
    private final CacheManager cacheManager;

    public void  evictCacheFromIngredient(String cacheName, String key){
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

}