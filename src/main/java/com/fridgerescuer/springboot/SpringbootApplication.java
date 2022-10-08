package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.cache.CacheUtil;
import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.databaseoperation.AutoCompleteInjector;
import com.fridgerescuer.springboot.databaseoperation.DBConverter;
import com.fridgerescuer.springboot.databaseoperation.ReferenceInjector;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(Config.class)
@SpringBootApplication()
public class SpringbootApplication implements CommandLineRunner {

	@Autowired
	private DBConverter dbConverter;
	@Autowired
	private ReferenceInjector referenceInjector;

	@Autowired
	private AutoCompleteInjector autoCompleteInjector;


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
/*
		for(int i=0; i<10 ; ++i){
			String a =cacheService.callCacheMethod("6301b03c2a86b05a127ad67b");
			System.out.println(a);
			String aa =cacheService.callCacheMethod("6301b03d2a86b05a127ad685");
			System.out.println(aa);
		}
		//referenceInjector.setReferenceWithRecipe();
*/

		autoCompleteInjector.generateAllAutoCompleteCache();
		log.info("==서버 초기 동작 끝~==");
	}

}