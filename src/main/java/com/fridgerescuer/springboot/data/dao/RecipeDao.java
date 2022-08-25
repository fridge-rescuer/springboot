package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeDao {

    Recipe save(Recipe recipe);

    Recipe findById(String id);
    Recipe findByName(String name);
    List<Recipe> findAllByContainName(String name);

    void updateRecipeById(String targetId, Recipe updateData);
    void deleteById(String targetId);

    void setProducerMemberOfRecipeById(String recipeId, Member producerMember);

    void addImage(String targetId, MultipartFile file) throws IOException;
    void addCommentToRecipe(String recipeId, Comment comment);
    void updateRating(String recipeId, double newRating, double originRating);
    void deleteRating(String recipeId, double rating);
}
