package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.CommentResponseDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper//(componentModel = "spring", uses = {})
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentResponseDTO commentToResponseDTO(Comment comment);
    CommentDTO commentToDTO(Comment comment);

    Comment DTOtoComment(CommentDTO commentDTO);

    Comment responseDTOtoComment(CommentResponseDTO commentResponseDTO);
}
