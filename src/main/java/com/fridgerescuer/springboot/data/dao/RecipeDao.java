package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeDao {

    RecipeDTO save(RecipeDTO recipeDTO);
    RecipeDTO saveRecipeByMemberId(String memberId, RecipeDTO recipeDTO);

    RecipeDTO findById(String id);
    List<RecipeDTO> findAllByName(String name);         //이름은 중복가능 이므로 단 한개만 찾는 기능은 x
    List<RecipeDTO> findAllByContainName(String name);
    List<CommentDTO> getCommentsByRecipeId(String recipeId);

    void updateRecipeById(String targetId, RecipeDTO updateDataDTO);
    void deleteById(String targetId);

    //void addImage(String targetId, MultipartFile file) throws IOException;
    void addCommentToRecipe(String recipeId, CommentDTO commentDTO);
    void updateRating(String recipeId, double newRating, double originRating);
    void deleteRating(String recipeId, double rating);
}
