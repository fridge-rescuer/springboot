package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.exception.exceptionimpl.NoSuchImageException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
public class ImageDaoTest {

    @Autowired
    private ImageDao imageDao;

    @Test
    void uploadAndDeleteImage() throws IOException {
        byte[] bytes = new byte[]{10,20,30};
        FileInputStream inputStream = new FileInputStream("src/test/resources/application.properties");

        MultipartFile multipartFile = new MockMultipartFile("application.properties",inputStream);

        String imageId = imageDao.uploadImage(multipartFile, "test");
        InputStream downloadStream = imageDao.downloadImageByImageId(imageId);

        byte[] uploadBytes = multipartFile.getInputStream().readAllBytes();
        byte[] downloadBytes = downloadStream.readAllBytes();

        for (int idx = 0; idx < downloadBytes.length; idx++) {
            Assertions.assertThat(uploadBytes[idx]).isEqualTo(downloadBytes[idx]);
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
