package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.config.Config;
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
	private  DBConverter dbConverter;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		log.info("==서버 초기 동작 시작==");
		//convertDocumentAddComponent();
		//renameFields();
		//dbConverter.convertRecipeJsonToDocument();
		//convertRecipeJsonToDocument();

		//dbConverter.injectRecipeToIngredient();


		log.info("==서버 초기 동작 끝~==");
	}

}