package com.fridgerescuer.springboot.data.entity;

import lombok.*;

//@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Component {
    private String kcal;
    private String water_g;
    private String protein_g;
    private String lipid_g;
    private String ash_g;
    private String carbohydrate_g;
    private String sugar_g;
    private String dietaryFiber_g;
    private String ca_mg;
    private String fe_mg;
    private String p_mg;
    private String k_mg;
    private String na_mg;
    private String vitaminA_mcg;
    private String retinol_mcg;
    private String betaCarotene_mcg;
    private String thiamin_mg;
    private String riboflavin_mg;
    private String niacin_mg;
    private String vitaminC_mg;
    private String vitaminD_mcg;
    private String cholesterol_mg;
    private String saturatedFattyAcid_g;
    private String transFat_g;
    private String refuse;

    public Component(String kcal, String water_g, String protein_g, String lipid_g, String ash_g, String carbohydrate_g, String sugar_g, String dietaryFiber_g, String ca_mg, String fe_mg, String p_mg, String k_mg, String na_mg, String vitaminA_mcg, String retinol_mcg, String betaCarotene_mcg, String thiamin_mg, String riboflavin_mg, String niacin_mg, String vitaminC_mg, String vitaminD_mcg, String cholesterol_mg, String saturatedFattyAcid_g, String transFat_g, String refuse) {
        this.kcal = kcal;
        this.water_g = water_g;
        this.protein_g = protein_g;
        this.lipid_g = lipid_g;
        this.ash_g = ash_g;
        this.carbohydrate_g = carbohydrate_g;
        this.sugar_g = sugar_g;
        this.dietaryFiber_g = dietaryFiber_g;
        this.ca_mg = ca_mg;
        this.fe_mg = fe_mg;
        this.p_mg = p_mg;
        this.k_mg = k_mg;
        this.na_mg = na_mg;
        this.vitaminA_mcg = vitaminA_mcg;
        this.retinol_mcg = retinol_mcg;
        this.betaCarotene_mcg = betaCarotene_mcg;
        this.thiamin_mg = thiamin_mg;
        this.riboflavin_mg = riboflavin_mg;
        this.niacin_mg = niacin_mg;
        this.vitaminC_mg = vitaminC_mg;
        this.vitaminD_mcg = vitaminD_mcg;
        this.cholesterol_mg = cholesterol_mg;
        this.saturatedFattyAcid_g = saturatedFattyAcid_g;
        this.transFat_g = transFat_g;
        this.refuse = refuse;
    }
}
