package com.fridgerescuer.springboot.data.dao.impl;

import com.fridgerescuer.springboot.data.dao.ImageDao;
import com.fridgerescuer.springboot.exception.exceptionimpl.NoSuchImageException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ImageDaoImpl implements ImageDao {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations operations;

    //파일 이름은 반드시 필요해서 카테코리 이름을 넘기는 걸로
    @Override
    public String uploadImage(MultipartFile imageFile, String category) {
        //BasicDBObject meta = new BasicDBObject();
        //meta.put("category", category);

        try{
            InputStream inputStream = imageFile.getInputStream();
            ObjectId id = gridFsTemplate.store(inputStream, category);

            log.info("image upload id ={}, category={}", id.toString(), category);
            return id.toString();
        }catch (IOException e){
            throw new RuntimeException(new IOException("upload Image IOException Occur!"));
        }

    }

    @Override
    public InputStream downloadImageByImageId(String imageId){
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(imageId)));

        if(file == null )   //존재하지 않는 id 접근
            throw new NoSuchImageException("image_id" + imageId + " no exist",new NullPointerException());

        GridFsResource resource = operations.getResource(file);

        try{
            return resource.getInputStream();
        }catch (IOException e){     //단순 IO 오류라 런타임 예외 처리, 발생 확률 없을 것으로 예상
            throw new RuntimeException(new IOException("image_id" + imageId + " download Exception Occur"));
        }
    }

    @Override
    public void deleteImageByImageId(String imageId) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(imageId)));
        log.info("delete image, id= {}", imageId);
    }
}
