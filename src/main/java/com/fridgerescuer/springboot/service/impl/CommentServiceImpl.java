package com.fridgerescuer.springboot.service.impl;

import com.fridgerescuer.springboot.data.dao.CommentDao;
import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.CommentResponseDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.mapper.CommentMapper;
import com.fridgerescuer.springboot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentDao commentDao;

    @Override
    public CommentResponseDTO saveComment(String memberId,String recipeId,CommentDTO commentDTO) {
        Comment savedComment = commentDao.save(memberId, recipeId, CommentMapper.INSTANCE.DTOtoComment(commentDTO));
        return CommentMapper.INSTANCE.commentToResponseDTO(savedComment);
    }

    @Override
    public CommentResponseDTO findCommentById(String commentId) {
        Comment foundComment = commentDao.findById(commentId);
        return CommentMapper.INSTANCE.commentToResponseDTO(foundComment);
    }

    @Override
    public void updateCommentById(String commentId, CommentDTO updateCommentDTO) {
        commentDao.updateComment(commentId, CommentMapper.INSTANCE.DTOtoComment(updateCommentDTO));
    }
}
