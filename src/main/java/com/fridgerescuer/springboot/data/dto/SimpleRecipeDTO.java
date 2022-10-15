package com.fridgerescuer.springboot.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleRecipeDTO implements Comparable<SimpleRecipeDTO>{
    private String name;
    private String id;
    private int cachePriority;

    @Override
    public int compareTo(SimpleRecipeDTO o) {
        if (this.cachePriority > o.getCachePriority())
            return 1;
        else if(this.cachePriority < o.getCachePriority())
            return -1;
        return 0;
    }
}

