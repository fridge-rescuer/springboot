package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.cache.CacheUtil;
import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.SimpleRecipe;
import com.fridgerescuer.springboot.cache.AutoCompleteUtils;
import com.fridgerescuer.springboot.databaseoperation.DBConverter;
import com.fridgerescuer.springboot.databaseoperation.ReferenceInjector;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.List;

@Slf4j
@Import(Config.class)
@SpringBootApplication()
public class SpringbootApplication implements CommandLineRunner {

	@Autowired
	private DBConverter dbConverter;
	@Autowired
	private ReferenceInjector referenceInjector;

	@Autowired
	private AutoCompleteUtils autoCompleteInjector;
	@Autowired
	private CacheUtil cacheUtil;
	@Autowired
	private RecipeDao recipeDao;


	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		log.info("==서버 초기 동작 시작==");
		//dbConverter.convertIngredientStringTypeToDouble();
		//dbConverter.setEntityClassForNationalDB();
		//dbConverter.convertIngredientAllTypeToDouble();
		//dbConverter.injectRecipeToIngredient();
		//initDataSet.setData();?

		//referenceInjector.setReferenceWithRecipe();


		/*	자동완성 초기 주입, DB 데이터 필요
		autoCompleteInjector.generateAllAutoCompleteCache();
		List<SimpleRecipe> searchResult = recipeDao.searchSimpleRecipeListByAutoComplete("고구마");
		for (SimpleRecipe result: searchResult) {
			log.info("name ={}, id={}", result.getName(), result.getId());
		}
		*/


		log.info("==서버 초기 동작 끝~==");
	}

}