package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
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
	private IngredientRepository ingredientRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		log.info("==서버 동작 테스트 시작==");
		ingredientRepository.deleteAll();

		ingredientRepository.save(new Ingredient("마늘", "채소"));
		ingredientRepository.save(new Ingredient("삼겹살", "돼지 고기"));


		for (Ingredient ingredient: ingredientRepository.findAll()){
			log.info("found : {}", ingredient.toString());
		}

		log.info("==서버 동작 테스트 끝~==");
	}
}
