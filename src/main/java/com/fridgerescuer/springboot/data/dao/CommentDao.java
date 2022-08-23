package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Comment;

public interface CommentDao {
    Comment save(String memberId, String recipeId, Comment comment);
}
