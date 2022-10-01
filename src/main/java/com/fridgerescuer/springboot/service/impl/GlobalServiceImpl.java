//package com.fridgerescuer.springboot.service.impl;
//
//import com.fridgerescuer.springboot.constants.ServiceType;
//import com.fridgerescuer.springboot.data.dao.CommentDao;
//import com.fridgerescuer.springboot.data.dao.RecipeDao;
//import com.fridgerescuer.springboot.service.GlobalService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class GlobalServiceImpl implements GlobalService {
//
//    @Autowired
//    private final RecipeDao recipeDao;
//    @Autowired
//    private final CommentDao commentDao;
//    /**
//     * Save image list in DB and return ID list
//     * @param List<MultipartFile> images
//     * @return image IDs saved in DB
//     */
//    @Override
//    public List<String> addImageToDB(List<MultipartFile> images, String id, ServiceType origin) {
//        List<String> imageIds = new ArrayList<>();
//        switch (origin.getValue()) {
//            case ServiceType.RECIPE: images.stream().forEach(image -> {
//                try {
//                    imageIds.add(recipeDao.addImage(id, image));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            case ServiceType.COMMENT: images.stream().forEach(image -> {
//                try {
//                    imageIds.add(commentDao.addImage(id, image));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//        return imageIds;
//    }
//}
