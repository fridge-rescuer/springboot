package com.fridgerescuer.springboot.data.gridfs;

import com.fridgerescuer.springboot.data.entity.Recipe;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class RecipeGridFsAccessObject {
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations operations;


    public String saveImageByGridFs(int manualIdx, Recipe recipe, InputStream inputStream){

        BasicDBObject meta = new BasicDBObject();
        if(manualIdx == -1) //-1인 경우 메인 image로 분류
            meta.put("isMainImage", true);
        else
            meta.put("manualIdx", manualIdx);
        meta.put("recipeId", recipe.getId());
        meta.put("recipeName", recipe.getName());

        ObjectId id = gridFsTemplate.store(inputStream, recipe.getName(), meta);

        return id.toString();
    }

    public void deleteImageByGridFsId(String imageId){
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(imageId)));
    }

}
