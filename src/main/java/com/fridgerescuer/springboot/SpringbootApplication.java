package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.dto.*;
import com.fridgerescuer.springboot.data.entity.Component;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.service.IngredientService;
import com.fridgerescuer.springboot.service.MemberService;
import com.fridgerescuer.springboot.service.RecipeService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Set;

@Slf4j
@Import(Config.class)
@SpringBootApplication()
public class SpringbootApplication implements CommandLineRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private RecipeService recipeService;

	@Autowired
	private MongoClient mongoClient;

	@Autowired
	MongoTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		log.info("==서버 동작 테스트 시작==");

		//convertDocumentAddComponent();


		log.info("==서버 동작 테스트 끝~==");
	}

	void convertDocumentAddComponent(){


		MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

		MongoCollection<Document> robosCollection = mongoDb.getCollection("fixIngredient"); //get the name of the collection that you want

		MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

		while(cursor.hasNext()){
			Document doc = cursor.next();
			String name = (String)doc.get("name");
			String  dataCode= (String)doc.get("dataCode");
			String dataTypeName = (String)doc.get("dataTypeName");
			String representationName = (String)doc.get("representationName");
			String originTypeName = (String)doc.get("originTypeName");
			String largeCategory = (String)doc.get("largeCategory");
			String mediumCategory = (String)doc.get("mediumCategory");
			String smallCategory = (String)doc.get("smallCategory");
			String subCategory = (String)doc.get("subCategory");



			String kcal = (String)doc.get("kcal");
			String water_g = (String)doc.get("water_g");
			String protein_g = (String)doc.get("protein_g");
			String lipid_g = (String)doc.get("lipid_g");
			String ash_g = (String)doc.get("ash_g");
			String carbohydrate_g = (String)doc.get("carbohydrate_g");
			String sugar_g = (String)doc.get("sugar_g");
			String dietaryFiber_g = (String)doc.get("dietaryFiber_g");
			String ca_mg = (String)doc.get("ca_mg");
			String fe_mg = (String)doc.get("fe_mg");
			String p_mg = (String)doc.get("p_mg");
			String k_mg = (String)doc.get("k_mg");
			String na_mg = (String)doc.get("na_mg");
			String vitaminA_mcg = (String)doc.get("vitaminA_mcg");
			String retinol_mcg = (String)doc.get("retinol_mcg");
			String betaCarotene_mcg = (String)doc.get("betaCarotene_mcg");
			String thiamin_mg = (String)doc.get("thiamin_mg");
			String riboflavin_mg = (String)doc.get("riboflavin_mg");
			String niacin_mg  = (String)doc.get("niacin_mg");
			String vitaminC_mg = (String)doc.get("vitaminC_mg");
			String vitaminD_mcg = (String)doc.get("vitaminD_mcg");
			String cholesterol_mg = (String)doc.get("cholesterol_mg");
			String saturatedFattyAcid_g = (String)doc.get("saturatedFattyAcid_g");
			String transFat_g = (String)doc.get("transFat_g");
			String refuse = (String)doc.get("refuse");

			Component component = new Component(kcal, water_g, protein_g, lipid_g, ash_g, carbohydrate_g, sugar_g, dietaryFiber_g, ca_mg, fe_mg, p_mg, k_mg, na_mg,
					vitaminA_mcg, retinol_mcg, betaCarotene_mcg, thiamin_mg, riboflavin_mg, niacin_mg, vitaminC_mg, vitaminD_mcg, cholesterol_mg, saturatedFattyAcid_g,
					transFat_g, refuse);


			Ingredient ingredient = new Ingredient(name, dataCode, dataTypeName, representationName,
					originTypeName, largeCategory, mediumCategory, smallCategory, subCategory,component);
			ingredientRepository.save(ingredient);
		}
		//cursor.forEachRemaining(System.out::println);
	}
}


/*
		BasicDBObject searchQuery = new BasicDBObject();
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.append("$rename",new BasicDBObject()
				.append("식품명", "name")
				.append("데이터구분코드", "dataCode")
				.append("데이터구분명", "dataTypeName")
				.append("식품기원명", "originTypeName")
				.append("식품대분류명", "largeCategory")
				.append("대표식품명", "representationName")
				.append("식품중분류명", "mediumCategory")
				.append("식품소분류명", "smallCategory")
				.append("식품세분류명", "subCategory")

				.append("에너지(kcal)", "kcal")
				.append("수분(g)", "water_g")
				.append("단백질(g)", "protein_g")
				.append("지질(g)", "lipid_g")
				.append("회분(g)", "ash_g")
				.append("탄수화물(g)", "carbohydrate_g")
				.append("당류(g)", "sugar_g")
				.append("총식이섬유(g)", "dietaryFiber_g")
				.append("칼슘(mg)", "ca_mg")
				.append("철(mg)", "fe_mg")
				.append("인(mg)", "p_mg")
				.append("칼륨(mg)", "k_mg")
				.append("나트륨(mg)", "na_mg")
				.append("비타민 A(μg RAE)", "vitaminA_mcg")
				.append("레티놀(μg)", "retinol_mcg")
				.append("베타카로틴(μg)", "betaCarotene_mcg")
				.append("티아민(mg)", "thiamin_mg")
				.append("리보플라빈(mg)", "riboflavin_mg")
				.append("니아신(mg)", "niacin_mg")
				.append("비타민 C(mg)", "vitaminC_mg")
				.append("비타민 D(μg)", "vitaminD_mcg")
				.append("콜레스테롤(mg)", "cholesterol_mg")
				.append("총포화지방산(g)", "saturatedFattyAcid_g")
				.append("트랜스지방산(g)", "transFat_g")
				.append("폐기율(%)", "refuse")
		);
		mongoTemplate.getCollection("testIngredient").updateMany(searchQuery,updateQuery);

		*/