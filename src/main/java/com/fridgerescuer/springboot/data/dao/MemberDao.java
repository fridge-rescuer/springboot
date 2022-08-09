package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;

import java.util.List;

public interface MemberDao {
    Member saveMember(Member member);
    Member findById(String memberId);
    void addIngredientsToMember(String memberId, List<Ingredient> ingredients);
    void addRecipeToMember(String memberId, Recipe recipe);

}
