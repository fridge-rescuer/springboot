package com.fridgerescuer.springboot.data.vo;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class ExpirationDataVO {

    private final IngredientDTO ingredientDTO;
    private final LocalDate expirationDate;
    private final boolean isNoExpiration;     //유통기한이 없는 식재료의 경우 true

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpirationDataVO that = (ExpirationDataVO) o;
        return isNoExpiration == that.isNoExpiration && ingredientDTO.equals(that.ingredientDTO) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientDTO, expirationDate, isNoExpiration);
    }
}
