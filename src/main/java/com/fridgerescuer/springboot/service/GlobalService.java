package com.fridgerescuer.springboot.service;

import com.fridgerescuer.springboot.constants.ServiceType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GlobalService {
    List<String> addImageToDB(List<MultipartFile> images, String id, ServiceType origin);
}
