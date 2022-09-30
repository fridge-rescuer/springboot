package com.fridgerescuer.springboot.data.entity.component;

import lombok.AllArgsConstructor;
import lombok.ToString;

//일반 성분
@ToString
@AllArgsConstructor
public class GeneralComponent {

    double energy_kcal;
    double water_g;
    double protein_g;
    double fat_g;
    double ash_g;
    double carbohydrate_g;
    double totalSugars_g;
    double sucrose_g;
    double glucose_g;
    double fructose_g;
    double lactose_g;
    double maltose_g;
    double galactose_g;
    double totalDietaryFiber_g;
    double waterSolubleDietaryFiber_g;
    double waterInsolubleDietaryFiber_g;


    double saltEquivalent_g;
    double refuse_percent;

}
