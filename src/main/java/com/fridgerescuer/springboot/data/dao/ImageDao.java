package com.fridgerescuer.springboot.data.dao;

import java.io.IOException;
import java.io.InputStream;

public interface ImageDao {

    //return GridFS ImageId,파일 이름은 반드시 필요해서 카테코리 이름을 넘기는 걸로
    String uploadImage(InputStream inputStream, String category);

    InputStream downloadImageByImageId(String imageId);

    void deleteImageByImageId(String imageId);
}
