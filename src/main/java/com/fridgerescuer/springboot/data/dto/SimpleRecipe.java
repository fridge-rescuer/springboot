package com.fridgerescuer.springboot.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleRecipe implements Comparable<SimpleRecipe>{
    private String name;
    private String id;
    private int cachePriority;

    @Override
    public int compareTo(SimpleRecipe o) {
        if (this.cachePriority > o.getCachePriority())
            return 1;
        else if(this.cachePriority < o.getCachePriority())
            return -1;
        return 0;
    }
}

