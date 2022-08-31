package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.entity.Comment;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.gridfs.RecipeGridFsAccessObject;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchIngredientException;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchMemberException;
import com.fridgerescuer.springboot.exception.data.repository.NoSuchRecipeException;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    private final RecipeGridFsAccessObject gridFsAO;

    @Override
    public Recipe save(Recipe recipe) {
        Recipe savedRecipe = repository.save(recipe);
        setReferenceWithIngredientsByName(savedRecipe.getIngredientNames(), savedRecipe);

        return savedRecipe;
    }

    @Override
    public Recipe findById(String id) {
        Optional<Recipe> foundRecipe = repository.findById(id);

        if (foundRecipe.isEmpty()){
            throw new NoSuchRecipeException(new NullPointerException("no such recipe id in Repository, id=" + id));
        }

        return foundRecipe.get();
    }

    @Override
    public Recipe findByName(String name) {
        Recipe findRecipe = repository.findByName(name);
        if(findRecipe == null){
            throw new NoSuchRecipeException(new NullPointerException("no such recipe name in Repository, name=" + name));
        }

        return findRecipe;
    }

    @Override
    public List<Recipe> findAllByContainName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*" + name + ".*"));    //name이 포함된 모든 이름에 대해 검색

        List<Recipe> foundRecipes = template.find(query, Recipe.class);

        if(foundRecipes.isEmpty()){
            throw new NoSuchRecipeException(new NullPointerException("no such recipe contain name in Repository, name=" + name));
         }

        return foundRecipes;
    }

    @Override
    public List<Comment> getCommentsByRecipeId(String recipeId) {
        Recipe foundRecipe = this.findById(recipeId);

        return foundRecipe.getComments();
    }

    @Override
    public void updateRecipeById(String targetId, Recipe updateData) {
        Recipe targetRecipe = this.findById(targetId);  //존재하지 않는 id면 여기서 예외 처리됨
        deleteReferenceWithIngredients(targetRecipe);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(targetId));

        Update update = new Update();
        update.set("name", updateData.getName());
        update.set("type", updateData.getType());
        update.set("ingredientNames", updateData.getIngredientNames());
        template.updateMulti(query, update, Recipe.class);

        setReferenceWithIngredientsByName(updateData.getIngredientNames(), targetRecipe);   //연관 관계 다시 맵핑

        log.info("update id={} to recipe data ={}", targetId, updateData);
    }

    @Override
    public void deleteById(String targetId) {
        Recipe targetRecipe = this.findById(targetId);

        if(targetRecipe.getImageId() != null)   //이미지 부터 제거거
           gridFsAO.deleteImageByGridFsId(targetRecipe.getImageId());

        repository.deleteById(targetId);

        log.info("delete recipe, id={}", targetId);
    }

    @Override
    public void setProducerMemberIByRecipeId(String recipeId, String producerMemberId){
        template.update(Recipe.class)
                .matching(where("id").is(recipeId))
                .apply(new Update().set("producerMemberId", producerMemberId))
                .first();
    }

    @Override
    public void addImage(String targetId, MultipartFile file) throws IOException {
        Recipe foundRecipe = this.findById(targetId);   //존재하지 않는 id는 여기서 예외 처리됨

        String imageId = gridFsAO.saveImageByGridFs(-1, foundRecipe, file.getInputStream());

        template.update(Recipe.class)
                .matching(where("id").is(targetId))
                .apply(new Update().set("imageId", imageId))
                .first();
    }

    @Override
    public void addCommentToRecipe(String recipeId, Comment comment) {
        Recipe recipe = this.findById(recipeId);

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
    public void updateRating(String recipeId, double newRating, double originRating) {
        Recipe recipe = this.findById(recipeId);
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
    public void deleteRating(String recipeId, double rating) {
        Recipe recipe = this.findById(recipeId);

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


    private void setReferenceWithIngredientsByName(String[] ingredientNames, Recipe recipe){
        if(ingredientNames== null || ingredientNames.length ==0)
            return;

        for (String name:ingredientNames) {
            template.update(Ingredient.class)
                    .matching(where("name").is(name))
                    .apply(new Update().push("recipes", recipe))
                    .first();
        }
    }

    private void deleteReferenceWithIngredients(Recipe recipe){
        for (String name: recipe.getIngredientNames()) {   // 기존 재료들과의 연관 관계 제거
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(name));
            Update referenceUpdate = new Update().pull("recipes", recipe);

            template.updateMulti(query, referenceUpdate, Ingredient.class);
        }
    }
}
