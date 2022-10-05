package com.fridgerescuer.springboot.cache;

import lombok.Getter;

@Getter
public enum CacheType {
    RECIPE("recipe", 5 * 60, 1000),
    INGREDIENT("ingredient", 5 * 60, 1000);
    //ARTIST_INFO("artistInfo", 24 * 60 * 60, 10000);

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

    private String cacheName;
    private int expiredAfterWrite;
    private int maximumSize;
}
