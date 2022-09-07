package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.CommentDao;
import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.gridfs.CommentGridFsAccessObject;
import com.fridgerescuer.springboot.data.mapper.CommentMapper;
import com.fridgerescuer.springboot.data.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Slf4j
@Repository
@Transactional
@Component  //빈으로 등록되어 다른 클래스가 인터페이스로 의존성 주입 받을 때 자동 등록됨
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final MongoTemplate template;

    @Autowired
    private final MemberDao memberDao;
    @Autowired
    private final RecipeDao recipeDao;

    @Autowired
    private final CommentGridFsAccessObject gridFsAO;


    //다른 dao를 접근해 처리하는건 일종의 월권행위로 판단되지만, 일단 dao 파트에 작성
    // 추후 검토 후에 서비스 계층으로 변경 요망(서비스 계층이 할일이 너무 많이 지는 문제도 고려해야함)
    @Override
    public CommentDTO save(String memberId, String recipeId, Comment comment){
        comment.setRecipeId(recipeId);      //애초에 comment 내에 담겨 와야 할 필요성이 느껴짐
        Comment savedComment = commentRepository.save(comment);

        memberDao.addCommentToMember(memberId, comment);
        recipeDao.addCommentToRecipe(recipeId, comment);

        log.info("save Comment ={}", savedComment);
        return CommentMapper.INSTANCE.commentToDTO(savedComment);
    }

    private Comment getCommentById(String commentId){
        Optional<Comment> foundComment = commentRepository.findById(commentId);

        if (foundComment.isEmpty()){
//            throw new NoSuchCommentException(new NullPointerException("no such comment id =" + commentId));
        }

        return foundComment.get();
    }

    @Override
    public CommentDTO findById(String commentId) {
        Comment foundComment = this.getCommentById(commentId);

        return CommentMapper.INSTANCE.commentToDTO(foundComment);
    }

    @Override
    public void addImage(String commentId, MultipartFile file) throws IOException {
        Comment comment = this.getCommentById(commentId);

        String originImageId = comment.getImageId();            //존재하던 이미지가 있었다면 삭제
        if(originImageId == null || originImageId.equals("")){
            gridFsAO.deleteImageByGridFsId(originImageId);
        }

        String imageId = gridFsAO.saveImageByGridFs(comment, file.getInputStream());

        template.update(Comment.class)
                .matching(where("id").is(commentId))
                .apply(new Update().set("imageId", imageId))
                .first();
    }

    @Override
    public void updateCommentById(String commentId, Comment updateData) {
        Comment originComment = this.getCommentById(commentId);

        if(originComment.getRating() != updateData.getRating()){    //평점에 변동이 있는 경우만 반영
            recipeDao.updateRating(originComment.getRecipeId(),updateData.getRating(), originComment.getRating());
        }

        // 이미지가 변경되면 기존 이미지 DB에서 제거
        if(updateData.getImageId() != null && updateData.getImageId() != originComment.getImageId()){
            gridFsAO.deleteImageByGridFsId(originComment.getImageId());
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(commentId));
        Update update = new Update().set("rating", updateData.getRating())
                .set("body",  updateData.getBody())
                .set("imageId", updateData.getImageId())
                .set("date", updateData.getDate());

        template.updateMulti(query, update, Comment.class);

        log.info("Update comment id={}, to data ={}", commentId,updateData.toString());
    }

    @Override
    public void deleteCommentById(String commentId) {
        Comment comment = this.getCommentById(commentId);
        recipeDao.deleteRating(comment.getRecipeId(), comment.getRating());

        commentRepository.deleteById(commentId);

        log.info("delete comment id={}", commentId);
    }

}
