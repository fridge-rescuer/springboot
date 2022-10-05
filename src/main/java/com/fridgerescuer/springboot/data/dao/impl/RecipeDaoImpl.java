package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.cache.CacheUtil;
import com.fridgerescuer.springboot.data.dao.ImageDao;
import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.CommentDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.mapper.CommentMapper;
import com.fridgerescuer.springboot.data.mapper.RecipeMapper;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.fridgerescuer.springboot.exception.errorcodeimpl.RecipeError;
import com.fridgerescuer.springboot.exception.exceptionimpl.RecipeException;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Component  //빈으로 등록되어 다른 클래스가 인터페이스로 의존성 주입 받을 때 자동 등록됨
@RequiredArgsConstructor
public class RecipeDaoImpl implements RecipeDao {

    @Autowired
    private final RecipeRepository repository;
    @Autowired
    private final MongoTemplate template;

    @Autowired
    private final MemberDao memberDao;
    @Autowired
    private final ImageDao imageDao;

    @Autowired
    private final CacheUtil cacheUtil;

    private final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;

    private void setReferenceWithIngredientsByIngredientIds(Recipe recipe){
        Set<String> ingredientIds = recipe.getIngredientIds();

        if(ingredientIds == null || ingredientIds.isEmpty())
            return;

        int modifiedCnt =0;

        Iterator<String> it = ingredientIds.iterator();
        while(it.hasNext()){
            UpdateResult result = template.update(Ingredient.class)
                    .matching(where("_id").is(it.next()))
                    .apply(new Update().push("recipes", recipe))
                    .first();
            modifiedCnt += result.getModifiedCount();
        }
        log.info(" setReferenceWithIngredientsByIngredientIds modify {}'document ", modifiedCnt);
    }


    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {        //member 없이 저장
        Recipe savedRecipe = repository.save(recipeMapper.DTOtoRecipe(recipeDTO));
        setReferenceWithIngredientsByIngredientIds(savedRecipe);

        return recipeMapper.recipeToDTO(savedRecipe);
    }

    @Override
    public RecipeDTO saveRecipeByMemberId(String memberId, RecipeDTO recipeDTO) {   //제작한 멤버id를 명시하여 저장
        memberDao.findById(memberId);   //존재 x 멤버는 예외 발생

        RecipeDTO savedRecipeDTO = this.save(recipeDTO);
        this.setProducerMemberIByRecipeId(savedRecipeDTO.getId(), memberId);

        memberDao.addRecipeToMember(memberId,savedRecipeDTO);

        log.info("saveRecipeByMemberId ={}",this.findById(savedRecipeDTO.getId()));
        return this.findById(savedRecipeDTO.getId());
    }

    private Recipe getRecipeById(String id){
        Optional<Recipe> foundRecipe = repository.findById(id);

        if (foundRecipe.isEmpty()){
            throw new RecipeException(RecipeError.NOT_EXIST);
        }

        return foundRecipe.get();
    }

    @Override
    @Cacheable(cacheNames = "recipe", key = "#id")
    public RecipeDTO findById(String id) {
        return recipeMapper.recipeToDTO(this.getRecipeById(id));
    }

    @Override
    public List<RecipeDTO> findAllByName(String name) {
        List<Recipe> recipes = repository.findAllByName(name);
        if(recipes == null || recipes.isEmpty()){
            throw new RecipeException(RecipeError.NOT_EXIST);
        }

        return recipeMapper.recipeListToDTOList(recipes);
    }

    @Override
    public List<RecipeDTO> findAllByContainName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*" + name + ".*"));    //name이 포함된 모든 이름에 대해 검색

        List<Recipe> foundRecipes = template.find(query, Recipe.class);

        if(foundRecipes.isEmpty()){
            throw new RecipeException(RecipeError.NOT_EXIST);
         }

        return recipeMapper.recipeListToDTOList(foundRecipes);
    }

    @Override
    public List<CommentDTO> getCommentsByRecipeId(String recipeId) {
        Recipe foundRecipe = this.getRecipeById(recipeId);

        return CommentMapper.INSTANCE.commentListToDTOList(foundRecipe.getComments());
    }

    @Override
    @CacheEvict(cacheNames = "recipe", key = "#p0")
    public void updateRecipeById(String targetId, RecipeDTO updateDataDTO) {
        Recipe targetRecipe = this.getRecipeById(targetId);  //존재하지 않는 id면 여기서 예외 처리됨
        cacheUtil.evictCacheFromRecipeReferenceIngredient(targetRecipe.getIngredientIds());
        deleteReferenceWithIngredients(targetRecipe);

        // 이미지가 변경되면 기존 이미지 DB에서 제거, 만약 updateDTO의 imageid가 Null이면 삭제됨
        if(targetRecipe.getImageId() != null && updateDataDTO.getImageId() != targetRecipe.getImageId()){
            imageDao.deleteImageByImageId(targetRecipe.getImageId());
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetId));

        Update update = new Update()
                .set("name", updateDataDTO.getName())
                .set("type", updateDataDTO.getType())
                .set("imageId", updateDataDTO.getImageId())
                .set("ingredientIds", updateDataDTO.getIngredientIds());
        template.updateMulti(query, update, Recipe.class);

        targetRecipe = this.getRecipeById(targetId);  //업데이트 내용 다시 가져옴
        setReferenceWithIngredientsByIngredientIds(targetRecipe);   //연관 관계 다시 맵핑

        log.info("update id={} to recipe data ={}", targetId, updateDataDTO);
    }

    private void deleteReferenceWithIngredients(Recipe recipe){
        if(recipe.getIngredientIds() == null || recipe.getIngredientIds().size() == 0)
            return;

        for (String ingredientId: recipe.getIngredientIds()) {   // 기존 재료들과의 연관 관계 제거
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(ingredientId));
            Update referenceUpdate = new Update().pull("recipes", recipe);

            template.updateMulti(query, referenceUpdate, Ingredient.class);
        }
    }

    @Override
    @CacheEvict(cacheNames = "recipe", key = "#p0")
    public void deleteById(String targetId) {
        Recipe targetRecipe = this.getRecipeById(targetId);
        cacheUtil.evictCacheFromRecipeReferenceIngredient(targetRecipe.getIngredientIds());

        if(targetRecipe.getImageId() != null)   //이미지 부터 제거거
            imageDao.deleteImageByImageId(targetRecipe.getImageId());

        repository.deleteById(targetId);

        log.info("delete recipe, id={}", targetId);
    }

    private void setProducerMemberIByRecipeId(String recipeId, String producerMemberId){
        template.update(Recipe.class)
                .matching(where("id").is(recipeId))
                .apply(new Update().set("producerMemberId", producerMemberId))
                .first();
    }

    @Override
    @CacheEvict(cacheNames = "recipe", key = "#p0")
    public void addCommentToRecipe(String recipeId, CommentDTO commentDTO) {

        Comment comment = CommentMapper.INSTANCE.DTOtoComment(commentDTO);

        Recipe recipe = this.getRecipeById(recipeId);
        cacheUtil.evictCacheFromRecipeReferenceIngredient(recipe.getIngredientIds());

        double ratingTotalSum = comment.getRating();
        double finalRatingAvg = ratingTotalSum;

        if(recipe.getComments() != null || recipe.getComments().size() > 0){   // comment가 이미 1개 이상 존재시
            ratingTotalSum = recipe.getRatingTotalSum() + comment.getRating();
            finalRatingAvg = ratingTotalSum/(recipe.getComments().size() +1);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(recipeId));
        Update referenceUpdate = new Update().push("comments", comment)
                .set("ratingTotalSum", ratingTotalSum)
                .set("ratingAvg", finalRatingAvg);

        template.updateMulti(query, referenceUpdate, Recipe.class);

        log.info("addCommentToMember recipeId={}, ratingTotalSum ={}, finalRatingAvg ={}", recipeId,ratingTotalSum,finalRatingAvg);
    }

    @Override
    @CacheEvict(cacheNames = "recipe", key = "#p0")
    public void updateRating(String recipeId, double newRating, double originRating) {
        Recipe recipe = this.getRecipeById(recipeId);
        cacheUtil.evictCacheFromRecipeReferenceIngredient(recipe.getIngredientIds());

        double totalSum = recipe.getRatingTotalSum() - originRating + newRating;
        double updatedRatingAvg = totalSum/ recipe.getComments().size();

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(recipeId));
        Update referenceUpdate = new Update().set("ratingTotalSum", totalSum)
                .set("ratingAvg", updatedRatingAvg);

        template.updateMulti(query, referenceUpdate, Recipe.class);

        log.info("update recipe id ={} to ratingAvg ={} ",recipeId ,updatedRatingAvg);
    }

    @Override
    @CacheEvict(cacheNames = "recipe", key = "#p0")
    public void deleteRating(String recipeId, double rating) {

        Recipe recipe = this.getRecipeById(recipeId);
        cacheUtil.evictCacheFromRecipeReferenceIngredient(recipe.getIngredientIds());

        double totalSum = 0;
        double updatedRatingAvg = 0;

        if(recipe.getComments().size() >1){
            totalSum = recipe.getRatingTotalSum() - rating;
            updatedRatingAvg = totalSum/ (recipe.getComments().size() -1);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(recipeId));
        Update referenceUpdate = new Update().set("ratingTotalSum", totalSum)
                .set("ratingAvg", updatedRatingAvg);

        template.updateMulti(query, referenceUpdate, Recipe.class);

        log.info("delete recipe id ={} of rating ={}, now avg={} ", recipeId,rating, updatedRatingAvg);
    }
}
