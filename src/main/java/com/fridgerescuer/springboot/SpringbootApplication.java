package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.entity.Component;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.MemberService;
import com.fridgerescuer.springboot.service.RecipeService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;

import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Import(Config.class)
@SpringBootApplication()
public class SpringbootApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		log.info("==서버 초기 동작 시작==");

		//convertDocumentAddComponent();
		//renameFields();
		//convertRecipeJsonToDocument();

		log.info("==서버 초기 동작 끝~==");
	}

}