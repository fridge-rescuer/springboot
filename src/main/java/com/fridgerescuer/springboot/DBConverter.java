package com.fridgerescuer.springboot;

import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.entity.DetailIngredient;
import com.fridgerescuer.springboot.data.entity.Component;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Recipe;
import com.fridgerescuer.springboot.data.repository.IngredientRepository;
import com.fridgerescuer.springboot.data.repository.RecipeRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Import(Config.class)
@org.springframework.stereotype.Component
public class DBConverter {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private MongoTemplate template;


    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations operations;


    public void convertNameToCategories(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("NationalDB_v2"); //get the name of the collection that you want

        MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol
        int count = 0;
        while(cursor.hasNext()) {
            Document doc = cursor.next();
            String name = doc.getString("name");
            String[] split = name.split(", ");
            String representationName = split[0];
            String largeCategory ="", mediumCategory="", smallCategory="", subCategory="";

            switch (split.length){
                case 5:
                    subCategory = split[4];
                case 4:
                    smallCategory = split[3];
                case 3:
                    mediumCategory = split[2];
                case 2:
                    largeCategory = split[1];
                    break;
                case 1:
                    break;
                default:
                    log.info("other length detected!!!!");
            }
            Object id = doc.get("_id");
            robosCollection.updateMany(Filters.eq("_id", id), Updates.set("representationName",representationName));
            robosCollection.updateMany(Filters.eq("_id", id), Updates.set("largeCategory", largeCategory));
            robosCollection.updateMany(Filters.eq("_id", id), Updates.set("mediumCategory", mediumCategory));
            robosCollection.updateMany(Filters.eq("_id", id), Updates.set("smallCategory", smallCategory));
            robosCollection.updateMany(Filters.eq("_id", id), Updates.set("subCategory", subCategory));

            log.info("count = {}" , count++);
        }

    }

    public void convertIngredientStringTypeToDouble(){
        String fieldName = "alanine_mg\talpalinolenicAcid_mg\tarachidicAcid_mg\tarachidonicAcid_mg\targinine_mg\tash_g\tasparticAcid_mg\tbatacarotene_micro_g\tbegenicAcid_mg\tbiotin_micro_g\tbutyricAcid_mg\tcalcium_mg\tcapricAcid_mg\tcaproicAcid_mg\tcaprylicAcid_mg\tcarbohydrate_g\tcholecalciferol_micro_g\tcholesterol_mg\tcopper_mg\tcysteine_mg\tdihomolinolenicAcid_mg\tdocosadienoicAcid_mg\tdocosahexaenoicAcid_mg\tdocosapentaenoicAcid_mg\teicosadienoicAcid_mg\teicosapentaenoicAcid_mg\teicosatrienoicAcid_mg\tenergy_kcal\tergocalciferol_micro_g\terucicAcid_mg\tfat_g\tfolate_micro_g\tfolicAcid_micro_g\tfoodFolate_micro_g\tfoodGroup\tfructose_g\tgadoleicAcid_mg\tgalactose_g\tgammalinolenicAcid_mg\tglucose_g\tglutamicAcid_mg\tglycine_mg\thenicosanoicAcid_mg\theptadecanoicAcid_mg\theptadecenoicAcid_mg\thistidine_mg\tiodine_micro_g\tiron_mg\tisoleucine_mg\tlactose_g\tlauricAcid_mg\tleucine_mg\tlignocericAcid_mg\tlinoleicAcid_mg\tlysine_mg\tmagnesium_mg\tmaltose_g\tmanganese_mg\tmenaquinone_micro_g\tmethionine_mg\tmolybdenum_micro_g\tmyristicAcid_mg\tmyristoleicAcid_mg\tname\tnervonicAcid_mg\tniacin_mg\tnicotinamide_mg\tnicotinicAcid_mg\toleicAcid_mg\tpalmiticAcid_mg\tpalmitoleicAcid_mg\tpantothenicAcid_mg\tpentadecanoicAcid_mg\tphenylalanine_mg\tphosphorus_mg\tphylloquinone_micro_g\tpotassium_mg\tproline_mg\tprotein_g\tpyridoxine_mg\trefuse_percent\tretinol_micro_g\triboflavin_mg\tsaltEquivalent_g\tselenium_micro_g\tserine_mg\tsodium_mg\tstearicAcid_mg\tsucrose_g\ttaurine_mg\tthiamin_mg\tthreonine_mg\ttocpha_mg\ttocphb_mg\ttocphd_mg\ttocphg_mg\ttoctra_mg\ttoctrb_mg\ttoctrd_mg\ttoctrg_mg\ttotalAminoAcids_mg\ttotalDietaryFiber_g\ttotalEssentialAminoAcids_mg\ttotalEssentialFattyAcids_g\ttotalFattyAcids_g\ttotalMonounsaturatedFattyAcids_g\ttotalN_3PolyunsaturatedFattyAcids_g\ttotalN_6PolyunsaturatedFattyAcids_g\ttotalNiacinEquivalents_mg\ttotalPolyunsaturatedFattyAcids_g\ttotalSaturatedFattyAcids_g\ttotalSugars_g\ttotalTransFattyAcids_g\ttotalUnsaturatedFattyAcids_g\ttrans_linoleicAcids_mg\ttrans_linolenicAcids_mg\ttrans_oleicAcids_mg\ttricosanoicAcid_mg\ttridecanoicAcid_mg\ttryptophan_mg\ttyrosine_mg\tvaccenicAcid_mg\tvaline_mg\tvitaminA_micro_g\tvitaminB12_micro_g\tvitaminB6_mg\tvitaminC_mg\tvitaminD_micro_g\tvitaminE_mg\tvitaminK_micro_g\twaterInsolubleDietaryFiber_g\twaterSolubleDietaryFiber_g\twater_g\tzinc_mg";

        String fieldNames[] = fieldName.split("\\t");

        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("NationalDB_v2"); //get the name of the collection that you want

        MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol
        int count = 0;
        while(cursor.hasNext()) {
            int modifeidCnt = 0;
            Document doc = cursor.next();
            Object id = doc.get("_id");
            for (String name: fieldNames) {
                Object value = doc.get(name);
                if(value.equals("-")) {
                    UpdateResult updateResult = robosCollection.updateMany(Filters.eq("_id", id), Updates.set(name, 0));
                    modifeidCnt += updateResult.getModifiedCount();
                }
            }
            log.info("count: {} finished,change: {}", ++count, modifeidCnt);
        }
    }

    public void createFiledName(){
        String prefix = "Column";

        String body ="Energy\n" +
                "Water\n" +
                "Protein\n" +
                "Fat\n" +
                "Ash\n" +
                "Carbohydrate\n" +
                "Total sugars\n" +
                "Sucrose\n" +
                "Glucose\n" +
                "Fructose\n" +
                "Lactose\n" +
                "Maltose\n" +
                "Galactose\n" +
                "Total dietary fiber\n" +
                "Water soluble dietary fiber\n" +
                "Water insoluble dietary fiber\n" +
                "Calcium\n" +
                "Iron\n" +
                "Magnesium\n" +
                "Phosphorus\n" +
                "Potassium\n" +
                "Sodium\n" +
                "Zinc\n" +
                "Copper\n" +
                "Manganese\n" +
                "Selenium\n" +
                "Molybdenum\n" +
                "Iodine\n" +
                "Vitamin A\n" +
                "Retinol\n" +
                "bataCarotene\n" +
                "Thiamin\n" +
                "Riboflavin\n" +
                "Niacin\n" +
                "Total niacin equivalents\n" +
                "Nicotinic acid\n" +
                "Nicotinamide\n" +
                "Pantothenic acid\n" +
                "Vitamin B6\n" +
                "Pyridoxine\n" +
                "Biotin\n" +
                "Folate\n" +
                "Food folate\n" +
                "Folic acid\n" +
                "Vitamin B12\n" +
                "Vitamin C\n" +
                "Vitamin D\n" +
                "Ergocalciferol\n" +
                "Cholecalciferol\n" +
                "Vitamin E\n" +
                "TOCPHA\n" +
                "TOCPHB\n" +
                "TOCPHG\n" +
                "TOCPHD\n" +
                "TOCTRA\n" +
                "TOCTRB\n" +
                "TOCTRG\n" +
                "TOCTRD\n" +
                "Vitamin K\n" +
                "Phylloquinone\n" +
                "Menaquinone\n" +
                "Total amino acids\n" +
                "Total essential amino acids\n" +
                "Isoleucine\n" +
                "Leucine\n" +
                "Lysine\n" +
                "Methionine\n" +
                "Phenylalanine\n" +
                "Threonine\n" +
                "Tryptophan\n" +
                "Valine\n" +
                "Histidine\n" +
                "Arginine\n" +
                "Tyrosine\n" +
                "Cysteine\n" +
                "Alanine\n" +
                "Aspartic acid\n" +
                "Glutamic acid\n" +
                "Glycine\n" +
                "Proline\n" +
                "Serine\n" +
                "Taurine\n" +
                "Cholesterol\n" +
                "Total fatty acids\n" +
                "Total essential fatty acids\n" +
                "Total saturated fatty acids\n" +
                "Butyric acid\n" +
                "Caproic acid\n" +
                "Caprylic acid\n" +
                "Capric acid\n" +
                "Lauric acid\n" +
                "Tridecanoic acid\n" +
                "Myristic acid\n" +
                "Pentadecanoic acid\n" +
                "Palmitic acid\n" +
                "Heptadecanoic acid\n" +
                "Stearic acid\n" +
                "Arachidic acid\n" +
                "Henicosanoic acid\n" +
                "Begenic acid\n" +
                "Tricosanoic acid\n" +
                "Lignoceric acid\n" +
                "Total unsaturated fatty acids\n" +
                "Total monounsaturated fatty acids\n" +
                "Myristoleic acid\n" +
                "Palmitoleic acid\n" +
                "Heptadecenoic acid\n" +
                "Oleic acid\n" +
                "Vaccenic acid\n" +
                "Gadoleic acid\n" +
                "Erucic acid\n" +
                "Nervonic acid\n" +
                "Total polyunsaturated fatty acids\n" +
                "Linoleic acid\n" +
                "alpaLinolenic acid\n" +
                "gammaLinolenic acid\n" +
                "Eicosadienoic acid\n" +
                "Dihomolinolenic acid\n" +
                "Eicosatrienoic acid\n" +
                "Arachidonic acid\n" +
                "Eicosapentaenoic acid\n" +
                "Docosadienoic acid\n" +
                "Docosapentaenoic acid\n" +
                "Docosahexaenoic acid\n" +
                "Total n_3 polyunsaturated fatty acids\n" +
                "Total n_6 polyunsaturated fatty acids\n" +
                "Total trans fatty acids\n" +
                "trans_Oleic acids\n" +
                "trans_Linoleic acids\n" +
                "trans_Linolenic acids\n" +
                "Salt equivalent\n" +
                "Refuse\n";
        String unit = "kcal\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "mg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "μg\n" +
                "μg\n" +
                "μg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "g\n" +
                "g\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "g\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "g\n" +
                "g\n" +
                "g\n" +
                "mg\n" +
                "mg\n" +
                "mg\n" +
                "g\n" +
                "percent\n";

        String[] split = body.split("\\n");
        String[] splitUnit = unit.split("\\n");

        for(int i =0; i< split.length;++i){
            String tailUnit = splitUnit[i];
            if(splitUnit[i].equals("μg"))
                tailUnit = "micro_g";

            String temp = split[i].toLowerCase(Locale.ENGLISH)+ "_" + tailUnit;
            String[] tempSplits = temp.split(" ");
            String result = tempSplits[0];
            for(int j=1; j< tempSplits.length ; ++j){
                String front = tempSplits[j].substring(0, 1).toUpperCase(Locale.ENGLISH);
                result = result + front + tempSplits[j].substring(1);
            }
            split[i] = result;
        }



        String result = "";
        int idx = 0;

        for(; idx< split.length;++idx){
            String column = prefix + (idx + 6);
            String full = "\"" + column + "\" : \"" + split[idx] + "\"" ;
            result = result + full + " , ";
        }

        log.info(result);

    }

    public void injectRecipeToIngredient(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("recipe"); //get the name of the collection that you want

        MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol
        int count = 0;
        while(cursor.hasNext())
        {
            Document doc = cursor.next();
            //doc = cursor.next();
            String ingredientData = doc.getString("ingredientData");
            Recipe recipe = recipeRepository.findById(doc.get("_id").toString()).get();


            List<String> ingredientName = new ArrayList<>();

            ingredientData = ingredientData.replaceAll("\\[", ",");
            String[] splitedData = ingredientData.split("\\n|:|-|\\)");

            for (int i=0; i< splitedData.length ; i++) {
                splitedData[i] = splitedData[i].replaceAll("\\(.*", " @");
            }

            for(int i=0; i< splitedData.length ; i++) {

                splitedData[i] = splitedData[i].replaceAll("[0-9]", "@");
                StringTokenizer stringTokenizer = new StringTokenizer(splitedData[i], ",");

                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    //log.info("token: {} ", token);
                    StringTokenizer innerTokenizer = new StringTokenizer(token, " ");
                    StringBuilder stringBuilder = new StringBuilder();

                    while (innerTokenizer.hasMoreTokens()) {
                        String innerToken = innerTokenizer.nextToken();

                        //log.info("inner token: {} ", innerToken);
                        if (innerToken.contains("@") || innerToken.contains("g") || innerToken.contains("m") || innerToken.contains("/")
                                || innerToken.contains("약간") || innerToken.contains("재료") || innerToken.contains("2인분") || innerToken.contains("[")
                                || innerToken.contains("]") || innerToken.contains(".") || innerToken.contains("4인분"))
                            continue;

                        stringBuilder.append(innerToken);
                        if (innerTokenizer.hasMoreTokens())
                            stringBuilder.append(" ");
                    }

                    String compactIngredientName = stringBuilder.toString();
                    if(compactIngredientName.length() ==0)
                        continue;

                    if(compactIngredientName.substring(compactIngredientName.length()-1).equals(" "))
                        compactIngredientName = compactIngredientName.substring(0,compactIngredientName.length()-1);

                    Query query = new Query();
                    query.addCriteria(Criteria.where("name").is(compactIngredientName));
                    DetailIngredient compactIngredient = template.findOne(query, DetailIngredient.class, "compactIngredient");

                    if(compactIngredient == null)
                        compactIngredient = template.save(DetailIngredient.builder().name(compactIngredientName).build());

                    template.update(DetailIngredient.class)
                            .matching(where("_id").is(compactIngredient.getId()))
                            .apply(new Update().push("recipes", recipe))
                            .first();
                }

            }

            log.info("count = {}", count++);
        }

    }

    void convertRecipeJsonToDocument(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("recipeSetting"); //get the name of the collection that you want

        MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

        int count = 0;
        while(cursor.hasNext()){
            Document doc = cursor.next();
            String name = doc.getString("name");
            String image = doc.getString("image");

            String sequence =doc.getString("sequence");
            String category = doc.getString("category");
            String cookingType = doc.getString("cookingType");
            String ingredientData = doc.getString("ingredientData");
            String carbohydrate_g = doc.getString("carbohydrate_g");
            String fat_g = doc.getString("fat_g");
            String kcal = doc.getString("kcal");
            String na_mg = doc.getString("na_mg");
            String protein_g = doc.getString("protein_g");

            String[] manuals = new String[20];
            String[] manualImages = new String[20];

            for (int idx = 1; idx <=9 ; idx++) {
                String manualName = "MANUAL0" + Integer.toString(idx);
                String imageName = "MANUAL_IMG0" + Integer.toString(idx);

                manuals[idx -1] = doc.getString(manualName);
                manualImages[idx -1] = doc.getString(imageName);
            }

            for (int idx = 10; idx <=20 ; idx++) {
                String manualName = "MANUAL" + Integer.toString(idx);
                String imageName = "MANUAL_IMG" + Integer.toString(idx);

                manuals[idx -1] = doc.getString(manualName);
                manualImages[idx -1] = doc.getString(imageName);
            }

            //일단 저장
            Recipe recipe = new Recipe(name, sequence, category, cookingType, ingredientData,
                    carbohydrate_g, fat_g, kcal, na_mg, protein_g,manuals);
            Recipe savedRecipe = template.save(recipe);


            InputStream inputStreamByUrl = getInputStreamByUrl(image);
            if(inputStreamByUrl != null){
                String imageId = addGridFs(-1, savedRecipe,inputStreamByUrl ).toString();   //대표 이미지가 없는 경우

                template.update(Recipe.class)
                        .matching(where("id").is(savedRecipe.getId()))
                        .apply(new Update().set("imageId", imageId))
                        .first();
            }


            for (int i = 0; i < 20; i++) {
                if(manualImages[i].length() == 0){
                    continue;
                }

                InputStream imageStream = getInputStreamByUrl(manualImages[i]);
                if(imageStream == null)
                    continue;

                String manualImageId = addGridFs(i, savedRecipe, imageStream);
                putGridFs(savedRecipe.getId(), manualImageId);
            }

            log.info("saved idx = {}", count++);
        }
    }

    private InputStream getInputStreamByUrl(String urlString){
        InputStream inputStream;
        try {
            inputStream = new URL(urlString).openStream();
            return inputStream;
        } catch (IOException e) {
            log.info("unable url ={}" , urlString);
        }
        return null;
    }

    private void putGridFs(String recipeId, String manualImageId){
        template.update(Recipe.class)
                .matching(where("_id").is(recipeId))
                .apply(new Update().push("manualImageIds", manualImageId))
                .first();
    }

    private String addGridFs(int manualIdx,Recipe recipe, InputStream inputStream){

        BasicDBObject meta = new BasicDBObject();
        if(manualIdx == -1)
            meta.put("isMainImage", true);
        else
            meta.put("manualIdx", manualIdx);
        meta.put("recipeId", recipe.getId());
        meta.put("recipeName", recipe.getName());

        ObjectId id = gridFsTemplate.store(inputStream, recipe.getName(), meta);

        return id.toString();
    }

    private Binary getBinaryImageByUrl(String urlString){
        try {
            URL url = new URL(urlString);
            BufferedImage downloadImage = ImageIO.read(url);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(downloadImage, "png", baos);

            Binary binaryImage = new Binary(BsonBinarySubType.BINARY, baos.toByteArray());
            return binaryImage;
        } catch (Exception e) {
            log.info("url error = {}", urlString);
        }

        return null;
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


            DetailIngredient ingredient = new DetailIngredient(name, dataCode, dataTypeName, representationName,
                    originTypeName, largeCategory, mediumCategory, smallCategory, subCategory,component);
            //ingredientRepository.save(ingredient);
        }
        //cursor.forEachRemaining(System.out::println);
    }

    private void renameFields(){
        BasicDBObject searchQuery = new BasicDBObject();
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$rename",new BasicDBObject()
                .append("RCP_SEQ", "sequence")
                .append("RCP_NM", "name")
                .append("RCP_WAY2", "cookingType")
                .append("RCP_PAT2", "category")
                .append("INFO_ENG", "kcal")
                .append("INFO_CAR", "carbohydrate_g")
                .append("INFO_PRO", "protein_g")
                .append("INFO_FAT", "fat_g")
                .append("INFO_NA", "na_mg")
                .append("HASH_TAG", "hashTag")
                .append("ATT_FILE_NO_MK", "image")
                .append("RCP_PARTS_DTLS", "ingredientData")
        );

        template.getCollection("recipeSetup").updateMany(searchQuery,updateQuery);
    }
}
