package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CommentDao {
    CommentDTO save(CommentDTO commentDTO);
    CommentDTO save(String memberId, String recipeId, CommentDTO commentDTO);

    CommentDTO findById(String commentId);

    //void addImage(String commentId, MultipartFile file) throws IOException;
    void updateCommentById(String commentId, CommentDTO updateDataDTO);

    void deleteCommentById(String commentId);
}
