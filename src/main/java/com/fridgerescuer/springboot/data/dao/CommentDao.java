package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CommentDao {
    Comment save(String memberId, String recipeId, Comment comment);

    Comment findById(String commentId);

    void addImage(String commentId, MultipartFile file) throws IOException;
    void updateCommentById(String commentId, Comment updateData);

    void deleteCommentById(String commentId);
}
