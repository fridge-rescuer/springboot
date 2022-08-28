package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.CommentResponseDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-28T14:46:00+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentResponseDTO commentToResponseDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseDTO.CommentResponseDTOBuilder commentResponseDTO = CommentResponseDTO.builder();

        commentResponseDTO.id( comment.getId() );
        commentResponseDTO.rating( comment.getRating() );
        commentResponseDTO.body( comment.getBody() );
        commentResponseDTO.imageId( comment.getImageId() );
        commentResponseDTO.date( comment.getDate() );
        commentResponseDTO.recipeId( comment.getRecipeId() );

        return commentResponseDTO.build();
    }

    @Override
    public CommentDTO commentToDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO.CommentDTOBuilder commentDTO = CommentDTO.builder();

        commentDTO.id( comment.getId() );
        commentDTO.rating( comment.getRating() );
        commentDTO.body( comment.getBody() );
        commentDTO.imageId( comment.getImageId() );
        commentDTO.date( comment.getDate() );
        commentDTO.recipeId( comment.getRecipeId() );

        return commentDTO.build();
    }

    @Override
    public Comment DTOtoComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.id( commentDTO.getId() );
        comment.rating( commentDTO.getRating() );
        comment.body( commentDTO.getBody() );
        comment.imageId( commentDTO.getImageId() );
        comment.date( commentDTO.getDate() );
        comment.recipeId( commentDTO.getRecipeId() );

        return comment.build();
    }

    @Override
    public Comment responseDTOtoComment(CommentResponseDTO commentResponseDTO) {
        if ( commentResponseDTO == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.id( commentResponseDTO.getId() );
        comment.rating( commentResponseDTO.getRating() );
        comment.body( commentResponseDTO.getBody() );
        comment.imageId( commentResponseDTO.getImageId() );
        comment.date( commentResponseDTO.getDate() );
        comment.recipeId( commentResponseDTO.getRecipeId() );

        return comment.build();
    }

    @Override
    public List<CommentResponseDTO> commentListToResponseDTOList(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponseDTO> list = new ArrayList<CommentResponseDTO>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToResponseDTO( comment ) );
        }

        return list;
    }
}
