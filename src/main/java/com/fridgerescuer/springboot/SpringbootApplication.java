package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.dto.MemberDto;
import com.fridgerescuer.springboot.data.dto.MemberResponseDto;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.service.MemberService;
import com.fridgerescuer.springboot.service.RecipeService;
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
	private MemberService memberService;

	@Autowired
	private RecipeService recipeService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		log.info("==서버 동작 테스트 시작==");

		//given
		MemberDto member = MemberDto.builder().name("우왁굳").build();
		RecipeDTO recipe = RecipeDTO.builder().name("감자 튀김").type("튀김").ingredientNames(new String[]{}).build();

		//when
		MemberResponseDto memberResponseDto = memberService.saveMember(member);
		recipeService.saveRecipeByMember(memberResponseDto.getId(), recipe);

		//then
		List<RecipeDTO> recipeDTOs = memberService.findMemberById(memberResponseDto.getId()).getRecipeDTOs();


		log.info("==서버 동작 테스트 끝~==");
	}
}
