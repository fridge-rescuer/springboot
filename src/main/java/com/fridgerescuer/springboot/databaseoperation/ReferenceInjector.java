package com.fridgerescuer.springboot.databaseoperation;

import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Import(Config.class)
@Component
public class ReferenceInjector {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private MongoTemplate template;


    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations operations;


    public void setReferenceWithRecipe(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> ingredientCollection = mongoDb.getCollection("ingredient"); //get the name of the collection that you want

        MongoCursor<Document> ingredientCursor =  ingredientCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

        MongoCollection<Document> recipeCollection = mongoDb.getCollection("recipe"); //get the name of the collection that you want

        MongoCursor<Document> recipeCursor =  recipeCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

        int count = 0;
        while(recipeCursor.hasNext()) {
            Document recipeDoc = recipeCursor.next();
            Object recipeId = recipeDoc.get("_id");

            String ingredientData = recipeDoc.getString("ingredientData");
            ingredientCursor = ingredientCollection.find().cursor();

            int updateCount = 0;

            while(ingredientCursor.hasNext()){
                Document ingredientDoc = ingredientCursor.next();
                Object id = ingredientDoc.get("_id");

                String representationName = ingredientDoc.getString("representationName");
                String largeCategory = ingredientDoc.getString("largeCategory");
                String mediumCategory = ingredientDoc.getString("mediumCategory");
                String smallCategory = ingredientDoc.getString("smallCategory");

                int priorityIndex = 0;
                if(ingredientData.contains(representationName)){
                    //priorityIndex += 1000;
                    Query query = new Query();
                    query.addCriteria(Criteria.where("id").is(id.toString()));

                    Recipe recipe = recipeRepository.findById(recipeId.toString()).get();

                    Update update = new Update();
                    update.push("recipes", recipe);

                    UpdateResult updateResult = template.updateMulti(query, update, Ingredient.class);
                    updateCount += updateResult.getModifiedCount();
                }
                /*
                if(!largeCategory.isEmpty() && ingredientData.contains(largeCategory))
                    priorityIndex += 100;
                if(!mediumCategory.isEmpty() && ingredientData.contains(mediumCategory))
                    priorityIndex += 10;
                if(!smallCategory.isEmpty() && ingredientData.contains(smallCategory))
                    priorityIndex += 1;

                if(priorityIndex >=1000)
                    log.info("use ingredient : {}", ingredientDoc.getString("name"));*/
            }

            log.info("count: {}, modified: {}", ++count , updateCount);
        }
    }


}
