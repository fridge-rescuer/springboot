package com.fridgerescuer.springboot.data.gridfs;

import com.fridgerescuer.springboot.data.entity.Comment;
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
public class CommentGridFsAccessObject {
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations operations;


    public String saveImageByGridFs(Comment comment, InputStream inputStream){

        BasicDBObject meta = new BasicDBObject();
        meta.put("commentId", comment.getId());

        ObjectId id = gridFsTemplate.store(inputStream, meta);

        return id.toString();
    }

    public void deleteImageByGridFsId(String commentId){
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(commentId)));
    }
}
