package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-10T23:40:39+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.1 (Oracle Corporation)"
)
public class CommentMapperImpl implements CommentMapper {

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
    public List<Comment> DTOListToCommentList(List<CommentDTO> commentDTOs) {
        if ( commentDTOs == null ) {
            return null;
        }

        List<Comment> list = new ArrayList<Comment>( commentDTOs.size() );
        for ( CommentDTO commentDTO : commentDTOs ) {
            list.add( DTOtoComment( commentDTO ) );
        }

        return list;
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
    public List<CommentDTO> commentListToDTOList(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToDTO( comment ) );
        }

        return list;
    }
}
