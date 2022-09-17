package com.fridgerescuer.springboot.data.vo;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class MemberVO {

    private final String id;
    private final String password;
    private final String name;

    private final List<ExpirationDataDTO> expirationDataDTOList;
    private final List<ExpirationDataDTO> privateExpirationDataDTOList;

    private final List<RecipeDTO> recipeDTOs;

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberVO memberVO = (MemberVO) o;
        return id.equals(memberVO.id) && password.equals(memberVO.password) && name.equals(memberVO.name);
    }
}
