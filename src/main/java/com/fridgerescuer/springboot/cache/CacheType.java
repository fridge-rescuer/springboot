package com.fridgerescuer.springboot.cache;

import lombok.Getter;

@Getter
public enum CacheType {
    RECIPE("recipe", 10 * 60, 1000),
    INGREDIENT("ingredient", 10 * 60, 1000),
    RECIPE_AUTO_COMPLETE("recipeAutoComplete", 24 * 60 * 60, 10000);
    //EXPIRATION_DATA("expirationData", 10 * 60, 1000);

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

    private String cacheName;
    private int expiredAfterWrite;
    private int maximumSize;
}
