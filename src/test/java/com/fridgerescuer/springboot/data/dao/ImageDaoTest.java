package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.exception.exceptionimpl.NoSuchImageException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
public class ImageDaoTest {

    @Autowired
    private ImageDao imageDao;

    @Test
    void uploadAndDeleteImage() throws IOException {
        byte[] bytes = new byte[]{10,20,30};
        InputStream inputStream = new ByteArrayInputStream(bytes);

        String imageId = imageDao.uploadImage(inputStream, "test");
        InputStream downloadStream = imageDao.downloadImageByImageId(imageId);

        byte[] downloadBytes = downloadStream.readAllBytes();   //다운로드 데이터와 비교
        for (int i=0; i<downloadBytes.length ; ++i) {
            Assertions.assertThat(downloadBytes[i]).isEqualTo(bytes[i]);
        }

        imageDao.deleteImageByImageId(imageId);

        Assertions.assertThatThrownBy(()->imageDao.downloadImageByImageId(imageId)) //삭제 확인
                .isInstanceOf(NoSuchImageException.class);
    }

    @Test
    void downloadNoExistImage(){
        Assertions.assertThatThrownBy(()->imageDao.downloadImageByImageId("1234"))
                .isInstanceOf(NoSuchImageException.class);
    }

}
