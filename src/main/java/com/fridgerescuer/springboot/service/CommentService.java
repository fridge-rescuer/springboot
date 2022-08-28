package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.CommentResponseDTO;

public interface CommentService {
    CommentResponseDTO saveComment(String memberId,String recipeId,CommentDTO commentDTO);

    CommentResponseDTO findCommentById(String commentId);
}
