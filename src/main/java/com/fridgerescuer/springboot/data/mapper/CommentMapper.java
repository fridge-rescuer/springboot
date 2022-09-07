package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(componentModel = "spring", uses = {})
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO commentToDTO(Comment comment);

    Comment DTOtoComment(CommentDTO commentDTO);

    List<CommentDTO> commentListToDTOList(List<Comment> comments);
}
