package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.CommentDao;
import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Slf4j
@Repository
@Transactional
@Component  //빈으로 등록되어 다른 클래스가 인터페이스로 의존성 주입 받을 때 자동 등록됨
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private final CommentRepository repository;
    @Autowired
    private final MongoTemplate template;

    @Autowired
    private final MemberDao memberDao;
    @Autowired
    private final RecipeDao recipeDao;


    //다른 dao를 접근해 처리하는건 일종의 월권행위로 판단되지만, 일단 dao 파트에 작성
    // 추후 검토 후에 서비스 계층으로 변경 요망(서비스 계층이 할일이 너무 많이 지는 문제도 고려해야함)
    @Override
    public Comment save(String memberId, String recipeId, Comment comment){
        Comment savedComment = repository.save(comment);

        memberDao.addCommentToMember(memberId, comment);
        recipeDao.addCommentToRecipe(recipeId, comment);

        log.info("save Comment ={}", savedComment);
        return savedComment;
    }

}
