package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.CommentDao;
import com.fridgerescuer.springboot.data.dao.ImageDao;
import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private final RecipeDao recipeDao;
    @Autowired
    private final CommentDao commentDao;
    @Autowired
    private final MemberDao memberDao;
    @Autowired
    private final ImageDao imageDao;

/*    @Override
    public List<RecipeDTO> loadAllRecipeList() {
        //성호가 구현한 리스트 호출 방식에 맞추어서

    }

    @Override
    public List<RecipeDTO> loadSortedRecipeList() {
        //성호가 구현한 리스트 호출 방식에 맞추어서

    }*/

    @Override
    public RecipeDTO createRecipe(RecipeDTO trySaveRecipe, String memberToken) {
        //멤버토큰에서 id 추출
        String memberId = null;
        trySaveRecipe.setProducerMemberId(memberId);
        RecipeDTO recipeDTO = recipeDao.save(trySaveRecipe);
        memberDao.addRecipeToMember(memberId, recipeDTO);

        return recipeDTO;
    }

    @Override
    public RecipeDTO getRecipeDetail(String recipeId) {
        return recipeDao.findById(recipeId);
    }

    @Override
    public RecipeDTO updateRecipe(RecipeDTO updatedRecipe) {
        recipeDao.updateRecipeById(updatedRecipe.getId(), updatedRecipe);
        return recipeDao.findById(updatedRecipe.getId());
    }

    @Override
    public void deleteRecipe(RecipeDTO deleteRecipe) {
        recipeDao.deleteById(deleteRecipe.getId());
    }

    @Override
    public void createRecipeComment(RecipeDTO recipeDTO, String memberToken, CommentDTO trySaveComment, MultipartFile image) {
        String imageId = null;
        if(!image.isEmpty())
            trySaveComment.setImageId(imageDao.uploadImage(image,"Comment"));

        //memberToken에서 id 추출
        String memberId = null;
        CommentDTO commentDTO = commentDao.save(trySaveComment);
        recipeDao.addCommentToRecipe(recipeDTO.getId(), commentDTO);
        memberDao.addCommentToMember(memberId, commentDTO);
    }

    @Override
    public void updateRecipeComment(CommentDTO commentDTO, String memberToken) {
        commentDao.updateCommentById(commentDTO.getId(), commentDTO);
    }

    @Override
    public void deleteRecipeComment(String commentId, String memberToken) {
        commentDao.deleteCommentById(commentId);
    }

    @Override
    public CommentDTO getRecipeComment(String commentId) {
        return commentDao.findById(commentId);
    }




}
