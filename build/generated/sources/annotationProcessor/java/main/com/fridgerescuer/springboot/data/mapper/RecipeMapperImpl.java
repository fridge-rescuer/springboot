package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Recipe;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-10T23:40:39+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.1 (Oracle Corporation)"
)
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public Recipe DTOtoRecipe(RecipeDTO recipe) {
        if ( recipe == null ) {
            return null;
        }

        Recipe.RecipeBuilder recipe1 = Recipe.builder();

        recipe1.id( recipe.getId() );
        recipe1.name( recipe.getName() );
        recipe1.type( recipe.getType() );
        recipe1.producerMemberId( recipe.getProducerMemberId() );
        recipe1.comments( commentDTOListToCommentList( recipe.getComments() ) );
        recipe1.ratingAvg( recipe.getRatingAvg() );
        recipe1.ratingTotalSum( recipe.getRatingTotalSum() );
        Set<String> set = recipe.getIngredientIds();
        if ( set != null ) {
            recipe1.ingredientIds( new LinkedHashSet<String>( set ) );
        }
        recipe1.imageId( recipe.getImageId() );

        return recipe1.build();
    }

    @Override
    public RecipeDTO recipeToDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO.RecipeDTOBuilder recipeDTO = RecipeDTO.builder();

        recipeDTO.id( recipe.getId() );
        recipeDTO.name( recipe.getName() );
        recipeDTO.type( recipe.getType() );
        Set<String> set = recipe.getIngredientIds();
        if ( set != null ) {
            recipeDTO.ingredientIds( new LinkedHashSet<String>( set ) );
        }
        recipeDTO.producerMemberId( recipe.getProducerMemberId() );
        recipeDTO.imageId( recipe.getImageId() );
        recipeDTO.comments( commentListToCommentDTOList( recipe.getComments() ) );
        recipeDTO.ratingAvg( recipe.getRatingAvg() );
        recipeDTO.ratingTotalSum( recipe.getRatingTotalSum() );

        return recipeDTO.build();
    }

    @Override
    public List<RecipeDTO> recipeListToDTOList(List<Recipe> recipes) {
        if ( recipes == null ) {
            return null;
        }

        List<RecipeDTO> list = new ArrayList<RecipeDTO>( recipes.size() );
        for ( Recipe recipe : recipes ) {
            list.add( recipeToDTO( recipe ) );
        }

        return list;
    }

    protected Comment commentDTOToComment(CommentDTO commentDTO) {
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

    protected List<Comment> commentDTOListToCommentList(List<CommentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDTO commentDTO : list ) {
            list1.add( commentDTOToComment( commentDTO ) );
        }

        return list1;
    }

    protected CommentDTO commentToCommentDTO(Comment comment) {
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

    protected List<CommentDTO> commentListToCommentDTOList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDTO> list1 = new ArrayList<CommentDTO>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToCommentDTO( comment ) );
        }

        return list1;
    }
}
