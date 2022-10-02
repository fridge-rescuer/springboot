package com.fridgerescuer.springboot.data.dao;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface ImageDao {

    //return GridFS ImageId,파일 이름은 반드시 필요해서 파일 이름으로서 카테코리 이름을 넘기는 걸로
    String uploadImage(MultipartFile imageFile, String category);

    InputStream downloadImageByImageId(String imageId);

    void deleteImageByImageId(String imageId);
}
