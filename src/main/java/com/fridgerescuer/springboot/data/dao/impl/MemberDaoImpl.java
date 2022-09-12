package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.ExpirationDataDao;
import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.*;
import com.fridgerescuer.springboot.data.mapper.*;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import com.fridgerescuer.springboot.exception.errorcodeimpl.MemberError;
import com.fridgerescuer.springboot.exception.exceptionimpl.MemberException;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private final MemberRepository repository;

    @Autowired
    private final MongoTemplate template;

    @Autowired
    private final ExpirationDataDao expirationDataDao;

    private final MemberMapper memberMapper = MemberMapper.INSTANCE;
    private final ExpirationDataMapper expirationDataMapper = ExpirationDataMapper.INSTANCE;
    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;

    @Override
    public MemberDTO saveMember(MemberDTO memberDTO) {
        Member savedMember = repository.save(memberMapper.DtoToMember(memberDTO));
        log.info("save Member ={}", savedMember);
        return memberMapper.memberToDto(savedMember);
    }

    private Member getMemberById(String memberId){
        Optional<Member> findMember = repository.findById(memberId);

        if(findMember.isEmpty()){
            throw new MemberException(MemberError.NOT_EXIST);
        }

        return findMember.get();
    }

    @Override
    public MemberDTO findById(String memberId) {
        Member foundMember = getMemberById(memberId);

        return memberMapper.memberToDto(foundMember);
    }

    @Override
    public void addExpirationDataToMember(String memberId, List<ExpirationDataDTO> expirationDataDTOList) {
        this.getMemberById(memberId);   //존재하는 멤버인지 확인

        if(expirationDataDTOList.size() ==0){
            log.debug("No Data in ExpirationDataList");
            return;
        }

        for (ExpirationDataDTO expData: expirationDataDTOList) {
            saveAndPushExpirationDataDTO(memberId, expData);
        }

    }

    private void saveAndPushExpirationDataDTO(String memberId, ExpirationDataDTO expirationDataDTO){
        ExpirationData savedData = expirationDataDao.saveExpirationData(expirationDataDTO);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));

        Update update = new Update();
        update.push("expirationDataList",savedData);

        template.updateMulti(query, update, Member.class);
        log.info("push Member ExpirationData ={}, to MemberId ={}",savedData ,memberId);
    }

    @Override
    public void addPrivateExpirationDataToMember(String memberId, List<ExpirationDataDTO> expirationDataDTOList) {
        this.getMemberById(memberId);   //존재하는 멤버인지 확인

        if(expirationDataDTOList.size() ==0){
            log.debug("No Data in ExpirationDataList");
            return;
        }

        for (ExpirationDataDTO expData: expirationDataDTOList) {
            saveAndPushPrivateExpirationDataDTO(memberId, expData);
        }

    }

    private void saveAndPushPrivateExpirationDataDTO(String memberId, ExpirationDataDTO expirationDataDTO){
        PrivateExpirationData savePrivateData = expirationDataDao.savePrivateExpirationData(expirationDataDTO);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));

        Update update = new Update();
        update.push("privateExpirationDataList",savePrivateData);

        template.updateMulti(query, update, Member.class);
        log.info("push Member PrivateExpirationData ={}, to MemberId ={}",savePrivateData ,memberId);
    }


    @Override
    public void addRecipeToMember(String memberId, RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.DTOtoRecipe(recipeDTO);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));

        log.info("converted recipe ={}",recipe);

        Update update = new Update();
        update.push("recipes",recipe);

        UpdateResult updateResult = template.updateMulti(query, update, Member.class);

        if(updateResult.getModifiedCount() == 0){ //write 가 실패한 경우
     //       throw new MemberException(new NullPointerException("no such member id in Repository, id=" + memberId));
        }

        log.info("add recipe ={}, to member id={}", recipe,memberId);
    }

    @Override
    public void addCommentToMember(String memberId,  CommentDTO commentDTO) {
        Comment comment = commentMapper.DTOtoComment(commentDTO);

        UpdateResult updateResult = template.update(Member.class)
                .matching(where("id").is(memberId))
                .apply(new Update().push("comments", comment))
                .first();

        if(updateResult.getModifiedCount() == 0){ //write 가 실패한 경우
            throw new MemberException(MemberError.NOT_EXIST);
        }

        log.info("addCommentToMember memberId={}", memberId);
    }

    @Override
    public void updateMemberById(String memberId, MemberDTO updateDataMemberDTO) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));

        Update update = new Update();
        update.set("name",updateDataMemberDTO.getName());

        UpdateResult updateResult = template.updateMulti(query, update, Member.class);

        if(updateResult.getModifiedCount() == 0){ //write 가 실패한 경우
   //         throw new MemberException(new NullPointerException("no such member id in Repository, id=" + memberId));
        }

        log.info("updateDataMemberDTO ={}", updateDataMemberDTO);
    }

    @Override
    public void deleteMemberById(String memberId) {
        this.getMemberById(memberId);    //존재하지 않는 id의 경우 예외 발생

        repository.deleteById(memberId);
        log.info("delete Member id ={}", memberId);
    }

}