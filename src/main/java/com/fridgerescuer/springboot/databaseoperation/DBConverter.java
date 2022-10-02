package com.fridgerescuer.springboot.databaseoperation;

import com.fridgerescuer.springboot.config.Config;
import com.fridgerescuer.springboot.data.entity.*;
import com.fridgerescuer.springboot.data.entity.component.*;
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
import java.util.*;

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


    public void setEntityClassForNationalDB(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> ingredientCollection = mongoDb.getCollection("NationalDB_v3"); //get the name of the collection that you want
        MongoCursor<Document> cursor =  ingredientCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

        int count = 0;
        while(cursor.hasNext()) {
            Document doc = cursor.next();

        double index = doc.getDouble("index");;

        String foodGroup= doc.getString("foodGroup");
        String foodCode= doc.getString("foodCode");
        String name= doc.getString("fullName");

        String representationName= doc.getString("representationName");
        String largeCategory= doc.getString("largeCategory");
        String mediumCategory= doc.getString("mediumCategory");
        String smallCategory= doc.getString("smallCategory");
        String subCategory= doc.getString("subCategory");

            double energy_kcal = doc.getDouble("energy_kcal");
            double water_g = doc.getDouble("water_g");
            double protein_g = doc.getDouble("protein_g");
            double fat_g = doc.getDouble("fat_g");
            double ash_g = doc.getDouble("ash_g");
            double carbohydrate_g = doc.getDouble("carbohydrate_g");
            double totalSugars_g = doc.getDouble("totalSugars_g");
            double sucrose_g = doc.getDouble("sucrose_g");
            double glucose_g = doc.getDouble("glucose_g");
            double fructose_g = doc.getDouble("fructose_g");
            double lactose_g = doc.getDouble("lactose_g");
            double maltose_g = doc.getDouble("maltose_g");
            double galactose_g = doc.getDouble("galactose_g");
            double totalDietaryFiber_g = doc.getDouble("totalDietaryFiber_g");
            double waterSolubleDietaryFiber_g = doc.getDouble("waterSolubleDietaryFiber_g");
            double waterInsolubleDietaryFiber_g = doc.getDouble("waterInsolubleDietaryFiber_g");
            double calcium_mg = doc.getDouble("calcium_mg");
            double iron_mg = doc.getDouble("iron_mg");
            double magnesium_mg = doc.getDouble("magnesium_mg");
            double phosphorus_mg = doc.getDouble("phosphorus_mg");
            double potassium_mg = doc.getDouble("potassium_mg");
            double sodium_mg = doc.getDouble("sodium_mg");
            double zinc_mg = doc.getDouble("zinc_mg");
            double copper_mg = doc.getDouble("copper_mg");
            double manganese_mg = doc.getDouble("manganese_mg");
            double selenium_micro_g = doc.getDouble("selenium_micro_g");
            double molybdenum_micro_g = doc.getDouble("molybdenum_micro_g");
            double iodine_micro_g = doc.getDouble("iodine_micro_g");
            double vitaminA_micro_g = doc.getDouble("vitaminA_micro_g");
            double retinol_micro_g = doc.getDouble("retinol_micro_g");
            double batacarotene_micro_g = doc.getDouble("batacarotene_micro_g");
            double thiamin_mg = doc.getDouble("thiamin_mg");
            double riboflavin_mg = doc.getDouble("riboflavin_mg");
            double niacin_mg = doc.getDouble("niacin_mg");
            double totalNiacinEquivalents_mg = doc.getDouble("totalNiacinEquivalents_mg");
            double nicotinicAcid_mg = doc.getDouble("nicotinicAcid_mg");
            double nicotinamide_mg = doc.getDouble("nicotinamide_mg");
            double pantothenicAcid_mg = doc.getDouble("pantothenicAcid_mg");
            double vitaminB6_mg = doc.getDouble("vitaminB6_mg");
            double pyridoxine_mg = doc.getDouble("pyridoxine_mg");
            double biotin_micro_g = doc.getDouble("biotin_micro_g");
            double folate_micro_g = doc.getDouble("folate_micro_g");
            double foodFolate_micro_g = doc.getDouble("foodFolate_micro_g");
            double folicAcid_micro_g = doc.getDouble("folicAcid_micro_g");
            double vitaminB12_micro_g = doc.getDouble("vitaminB12_micro_g");
            double vitaminC_mg = doc.getDouble("vitaminC_mg");
            double vitaminD_micro_g = doc.getDouble("vitaminD_micro_g");
            double ergocalciferol_micro_g = doc.getDouble("ergocalciferol_micro_g");
            double cholecalciferol_micro_g = doc.getDouble("cholecalciferol_micro_g");
            double vitaminE_mg = doc.getDouble("vitaminE_mg");
            double tocpha_mg = doc.getDouble("tocpha_mg");
            double tocphb_mg = doc.getDouble("tocphb_mg");
            double tocphg_mg = doc.getDouble("tocphg_mg");
            double tocphd_mg = doc.getDouble("tocphd_mg");
            double toctra_mg = doc.getDouble("toctra_mg");
            double toctrb_mg = doc.getDouble("toctrb_mg");
            double toctrg_mg = doc.getDouble("toctrg_mg");
            double toctrd_mg = doc.getDouble("toctrd_mg");
            double vitaminK_micro_g = doc.getDouble("vitaminK_micro_g");
            double phylloquinone_micro_g = doc.getDouble("phylloquinone_micro_g");
            double menaquinone_micro_g = doc.getDouble("menaquinone_micro_g");
            double totalAminoAcids_mg = doc.getDouble("totalAminoAcids_mg");
            double totalEssentialAminoAcids_mg = doc.getDouble("totalEssentialAminoAcids_mg");
            double isoleucine_mg = doc.getDouble("isoleucine_mg");
            double leucine_mg = doc.getDouble("leucine_mg");
            double lysine_mg = doc.getDouble("lysine_mg");
            double methionine_mg = doc.getDouble("methionine_mg");
            double phenylalanine_mg = doc.getDouble("phenylalanine_mg");
            double threonine_mg = doc.getDouble("threonine_mg");
            double tryptophan_mg = doc.getDouble("tryptophan_mg");
            double valine_mg = doc.getDouble("valine_mg");
            double histidine_mg = doc.getDouble("histidine_mg");
            double arginine_mg = doc.getDouble("arginine_mg");
            double tyrosine_mg = doc.getDouble("tyrosine_mg");
            double cysteine_mg = doc.getDouble("cysteine_mg");
            double alanine_mg = doc.getDouble("alanine_mg");
            double asparticAcid_mg = doc.getDouble("asparticAcid_mg");
            double glutamicAcid_mg = doc.getDouble("glutamicAcid_mg");
            double glycine_mg = doc.getDouble("glycine_mg");
            double proline_mg = doc.getDouble("proline_mg");
            double serine_mg = doc.getDouble("serine_mg");
            double taurine_mg = doc.getDouble("taurine_mg");
            double cholesterol_mg = doc.getDouble("cholesterol_mg");
            double totalFattyAcids_g = doc.getDouble("totalFattyAcids_g");
            double totalEssentialFattyAcids_g = doc.getDouble("totalEssentialFattyAcids_g");
            double totalSaturatedFattyAcids_g = doc.getDouble("totalSaturatedFattyAcids_g");
            double butyricAcid_mg = doc.getDouble("butyricAcid_mg");
            double caproicAcid_mg = doc.getDouble("caproicAcid_mg");
            double caprylicAcid_mg = doc.getDouble("caprylicAcid_mg");
            double capricAcid_mg = doc.getDouble("capricAcid_mg");
            double lauricAcid_mg = doc.getDouble("lauricAcid_mg");
            double tridecanoicAcid_mg = doc.getDouble("tridecanoicAcid_mg");
            double myristicAcid_mg = doc.getDouble("myristicAcid_mg");
            double pentadecanoicAcid_mg = doc.getDouble("pentadecanoicAcid_mg");
            double palmiticAcid_mg = doc.getDouble("palmiticAcid_mg");
            double heptadecanoicAcid_mg = doc.getDouble("heptadecanoicAcid_mg");
            double stearicAcid_mg = doc.getDouble("stearicAcid_mg");
            double arachidicAcid_mg = doc.getDouble("arachidicAcid_mg");
            double henicosanoicAcid_mg = doc.getDouble("henicosanoicAcid_mg");
            double begenicAcid_mg = doc.getDouble("begenicAcid_mg");
            double tricosanoicAcid_mg = doc.getDouble("tricosanoicAcid_mg");
            double lignocericAcid_mg = doc.getDouble("lignocericAcid_mg");
            double totalUnsaturatedFattyAcids_g = doc.getDouble("totalUnsaturatedFattyAcids_g");
            double totalMonounsaturatedFattyAcids_g = doc.getDouble("totalMonounsaturatedFattyAcids_g");
            double myristoleicAcid_mg = doc.getDouble("myristoleicAcid_mg");
            double palmitoleicAcid_mg = doc.getDouble("palmitoleicAcid_mg");
            double heptadecenoicAcid_mg = doc.getDouble("heptadecenoicAcid_mg");
            double oleicAcid_mg = doc.getDouble("oleicAcid_mg");
            double vaccenicAcid_mg = doc.getDouble("vaccenicAcid_mg");
            double gadoleicAcid_mg = doc.getDouble("gadoleicAcid_mg");
            double erucicAcid_mg = doc.getDouble("erucicAcid_mg");
            double nervonicAcid_mg = doc.getDouble("nervonicAcid_mg");
            double totalPolyunsaturatedFattyAcids_g = doc.getDouble("totalPolyunsaturatedFattyAcids_g");
            double linoleicAcid_mg = doc.getDouble("linoleicAcid_mg");
            double alpalinolenicAcid_mg = doc.getDouble("alpalinolenicAcid_mg");
            double gammalinolenicAcid_mg = doc.getDouble("gammalinolenicAcid_mg");
            double eicosadienoicAcid_mg = doc.getDouble("eicosadienoicAcid_mg");
            double dihomolinolenicAcid_mg = doc.getDouble("dihomolinolenicAcid_mg");
            double eicosatrienoicAcid_mg = doc.getDouble("eicosatrienoicAcid_mg");
            double arachidonicAcid_mg = doc.getDouble("arachidonicAcid_mg");
            double eicosapentaenoicAcid_mg = doc.getDouble("eicosapentaenoicAcid_mg");
            double docosadienoicAcid_mg = doc.getDouble("docosadienoicAcid_mg");
            double docosapentaenoicAcid_mg = doc.getDouble("docosapentaenoicAcid_mg");
            double docosahexaenoicAcid_mg = doc.getDouble("docosahexaenoicAcid_mg");
            double totalN_3PolyunsaturatedFattyAcids_g = doc.getDouble("totalN_3PolyunsaturatedFattyAcids_g");
            double totalN_6PolyunsaturatedFattyAcids_g = doc.getDouble("totalN_6PolyunsaturatedFattyAcids_g");
            double totalTransFattyAcids_g = doc.getDouble("totalTransFattyAcids_g");
            double trans_oleicAcids_mg = doc.getDouble("trans_oleicAcids_mg");
            double trans_linoleicAcids_mg = doc.getDouble("trans_linoleicAcids_mg");
            double trans_linolenicAcids_mg = doc.getDouble("trans_linolenicAcids_mg");
            double saltEquivalent_g = doc.getDouble("saltEquivalent_g");
            double refuse_percent = doc.getDouble("refuse_percent");

        GeneralComponent generalComponent = new GeneralComponent(energy_kcal, water_g, protein_g, fat_g, ash_g, carbohydrate_g, totalSugars_g, sucrose_g, glucose_g, fructose_g, lactose_g, maltose_g, galactose_g, totalDietaryFiber_g, waterSolubleDietaryFiber_g, waterInsolubleDietaryFiber_g, saltEquivalent_g, refuse_percent);
        Mineral mineral = new Mineral(calcium_mg, iron_mg, magnesium_mg, phosphorus_mg, potassium_mg, sodium_mg, zinc_mg, copper_mg, manganese_mg, selenium_micro_g, molybdenum_micro_g, iodine_micro_g);
        Vitamin vitamin = new Vitamin(vitaminA_micro_g , retinol_micro_g , batacarotene_micro_g , thiamin_mg , riboflavin_mg , niacin_mg , totalNiacinEquivalents_mg , nicotinicAcid_mg , nicotinamide_mg , pantothenicAcid_mg , vitaminB6_mg , pyridoxine_mg , biotin_micro_g , folate_micro_g , foodFolate_micro_g , folicAcid_micro_g , vitaminB12_micro_g , vitaminC_mg , vitaminD_micro_g , ergocalciferol_micro_g , cholecalciferol_micro_g , vitaminE_mg , tocpha_mg , tocphb_mg , tocphg_mg , tocphd_mg , toctra_mg , toctrb_mg , toctrg_mg , toctrd_mg , vitaminK_micro_g , phylloquinone_micro_g , menaquinone_micro_g );
        AminoAcid aminoAcid = new AminoAcid(totalAminoAcids_mg , totalEssentialAminoAcids_mg , isoleucine_mg , leucine_mg , lysine_mg , methionine_mg , phenylalanine_mg , threonine_mg , tryptophan_mg , valine_mg , histidine_mg , arginine_mg , tyrosine_mg , cysteine_mg , alanine_mg , asparticAcid_mg , glutamicAcid_mg , glycine_mg , proline_mg , serine_mg , taurine_mg);
        FattyAcid fattyAcid = new FattyAcid(cholesterol_mg , totalFattyAcids_g , totalEssentialFattyAcids_g , totalSaturatedFattyAcids_g , butyricAcid_mg , caproicAcid_mg , caprylicAcid_mg , capricAcid_mg , lauricAcid_mg , tridecanoicAcid_mg , myristicAcid_mg , pentadecanoicAcid_mg , palmiticAcid_mg , heptadecanoicAcid_mg , stearicAcid_mg , arachidicAcid_mg , henicosanoicAcid_mg , begenicAcid_mg , tricosanoicAcid_mg , lignocericAcid_mg , totalUnsaturatedFattyAcids_g , totalMonounsaturatedFattyAcids_g , myristoleicAcid_mg , palmitoleicAcid_mg , heptadecenoicAcid_mg , oleicAcid_mg , vaccenicAcid_mg ,
                gadoleicAcid_mg , erucicAcid_mg , nervonicAcid_mg , totalPolyunsaturatedFattyAcids_g , linoleicAcid_mg , alpalinolenicAcid_mg , gammalinolenicAcid_mg , eicosadienoicAcid_mg , dihomolinolenicAcid_mg , eicosatrienoicAcid_mg , arachidonicAcid_mg , eicosapentaenoicAcid_mg , docosadienoicAcid_mg , docosapentaenoicAcid_mg , docosahexaenoicAcid_mg , totalN_3PolyunsaturatedFattyAcids_g , totalN_6PolyunsaturatedFattyAcids_g , totalTransFattyAcids_g , trans_oleicAcids_mg , trans_linoleicAcids_mg , trans_linolenicAcids_mg);


            Ingredient newIngredientBuilder = Ingredient.builder().index(index).foodGroup(foodGroup).foodCode(foodCode).name(name).representationName(representationName)
                .largeCategory(largeCategory).mediumCategory(mediumCategory)
                .smallCategory(smallCategory).subCategory(subCategory).generalComponent(generalComponent).mineral(mineral).vitamin(vitamin).aminoAcid(aminoAcid).fattyAcid(fattyAcid).build();


        ingredientRepository.save(newIngredientBuilder);

        log.info("count : {} , finished", ++count);
        }
    }

    public void analyzeFoodCodeToInsertCategory(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> ingredientCollection = mongoDb.getCollection("NationalDB_v3"); //get the name of the collection that you want
        MongoCursor<Document> cursor =  ingredientCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

        int count = 0;
        while(cursor.hasNext()) {
            Document doc = cursor.next();

            String foodCode = doc.getString("foodCode");
            String representationCode = foodCode.substring(1, 4);
            String kindAndTypeCod = foodCode.substring(4, 7);
            String regionAndCharacterCod = foodCode.substring(7,10);
            String stateCod = foodCode.substring(10,11);
            String cookingMethodCod = foodCode.substring(11);

            String representationName ="";
            String kindAndType ="";
            String regionAndCharacter ="";
            String state ="";
            String cookingMethod ="";


            String fullName = doc.getString("fullName");
            String[] splitFullName = fullName.split(", ");

            int idx= 0;

            if(!kindAndTypeCod.equals("000")){
                kindAndType = splitFullName[++idx];
            }if(!regionAndCharacterCod.equals("000")){
                regionAndCharacter = splitFullName[++idx];
            }if(!stateCod.equals("0")){
                state = splitFullName[++idx];
            }if(!cookingMethodCod.equals("0")){
                cookingMethod = splitFullName[++idx];
            }



        }
    }

    public void addFoodCode(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> ingredientCollection = mongoDb.getCollection("NationalDB_v3"); //get the name of the collection that you want

        MongoCollection<Document> foodCodeCollection = mongoDb.getCollection("foodCode"); //get the name of the collection that you want

        MongoCursor<Document> foodCodeCursor =  foodCodeCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

        int count = 0;
        while(foodCodeCursor.hasNext()) {
            Document foodCodeDoc = foodCodeCursor.next();
            Object idx = foodCodeDoc.get("idx");
            String foodCodeDocString = foodCodeDoc.getString("Column2");

            ingredientCollection.updateMany(Filters.eq("Column1", idx), Updates.set("foodCode",foodCodeDocString));
            log.info("count: {}", ++count);
        }

    }

    public void convertNameToCategories(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("NationalDB_v3"); //get the name of the collection that you want

        MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol
        int count = 0;
        while(cursor.hasNext()) {
            Document doc = cursor.next();
            String name = doc.getString("fullName");
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
        String fieldName = "alanine_mg\talpalinolenicAcid_mg\tarachidicAcid_mg\tarachidonicAcid_mg\targinine_mg\tash_g\tasparticAcid_mg\tbatacarotene_micro_g\tbegenicAcid_mg\tbiotin_micro_g\tbutyricAcid_mg\tcalcium_mg\tcapricAcid_mg\tcaproicAcid_mg\tcaprylicAcid_mg\tcarbohydrate_g\tcholecalciferol_micro_g\tcholesterol_mg\tcopper_mg\tcysteine_mg\tdihomolinolenicAcid_mg\tdocosadienoicAcid_mg\tdocosahexaenoicAcid_mg\tdocosapentaenoicAcid_mg\teicosadienoicAcid_mg\teicosapentaenoicAcid_mg\teicosatrienoicAcid_mg\tenergy_kcal\tergocalciferol_micro_g\terucicAcid_mg\tfat_g\tfolate_micro_g\tfolicAcid_micro_g\tfoodCode\tfoodFolate_micro_g\tfoodGroup\tfructose_g\tfullName\tgadoleicAcid_mg\tgalactose_g\tgammalinolenicAcid_mg\tglucose_g\tglutamicAcid_mg\tglycine_mg\thenicosanoicAcid_mg\theptadecanoicAcid_mg\theptadecenoicAcid_mg\thistidine_mg\tindex\tiodine_micro_g\tiron_mg\tisoleucine_mg\tlactose_g\tlauricAcid_mg\tleucine_mg\tlignocericAcid_mg\tlinoleicAcid_mg\tlysine_mg\tmagnesium_mg\tmaltose_g\tmanganese_mg\tmenaquinone_micro_g\tmethionine_mg\tmolybdenum_micro_g\tmyristicAcid_mg\tmyristoleicAcid_mg\tnervonicAcid_mg\tniacin_mg\tnicotinamide_mg\tnicotinicAcid_mg\toleicAcid_mg\tpalmiticAcid_mg\tpalmitoleicAcid_mg\tpantothenicAcid_mg\tpentadecanoicAcid_mg\tphenylalanine_mg\tphosphorus_mg\tphylloquinone_micro_g\tpotassium_mg\tproline_mg\tprotein_g\tpyridoxine_mg\trefuse_percent\tretinol_micro_g\triboflavin_mg\tsaltEquivalent_g\tselenium_micro_g\tserine_mg\tsodium_mg\tstearicAcid_mg\tsucrose_g\ttaurine_mg\tthiamin_mg\tthreonine_mg\ttocpha_mg\ttocphb_mg\ttocphd_mg\ttocphg_mg\ttoctra_mg\ttoctrb_mg\ttoctrd_mg\ttoctrg_mg\ttotalAminoAcids_mg\ttotalDietaryFiber_g\ttotalEssentialAminoAcids_mg\ttotalEssentialFattyAcids_g\ttotalFattyAcids_g\ttotalMonounsaturatedFattyAcids_g\ttotalN_3PolyunsaturatedFattyAcids_g\ttotalN_6PolyunsaturatedFattyAcids_g\ttotalNiacinEquivalents_mg\ttotalPolyunsaturatedFattyAcids_g\ttotalSaturatedFattyAcids_g\ttotalSugars_g\ttotalTransFattyAcids_g\ttotalUnsaturatedFattyAcids_g\ttrans_linoleicAcids_mg\ttrans_linolenicAcids_mg\ttrans_oleicAcids_mg\ttricosanoicAcid_mg\ttridecanoicAcid_mg\ttryptophan_mg\ttyrosine_mg\tvaccenicAcid_mg\tvaline_mg\tvitaminA_micro_g\tvitaminB12_micro_g\tvitaminB6_mg\tvitaminC_mg\tvitaminD_micro_g\tvitaminE_mg\tvitaminK_micro_g\twaterInsolubleDietaryFiber_g\twaterSolubleDietaryFiber_g\twater_g\tzinc_mg";
        String fieldNames[] = fieldName.split("\\t");

        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("NationalDB_v3"); //get the name of the collection that you want

        MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol
        int count = 0;
        while(cursor.hasNext()) {
            int modifeidCnt = 0;
            Document doc = cursor.next();
            Object id = doc.get("_id");
            for (String name: fieldNames) {
                Object value = doc.get(name);
                if(value instanceof Integer) {
                    double doubleValue = ((Integer) value).doubleValue();
                    UpdateResult updateResult = robosCollection.updateMany(Filters.eq("_id", id), Updates.set(name, doubleValue));
                    modifeidCnt += updateResult.getModifiedCount();
                }
            }
            log.info("count: {} finished,change: {}", ++count, modifeidCnt);
        }
    }

    public void convertIngredientAllTypeToDouble(){
        String fieldName = "alanine_mg\talpalinolenicAcid_mg\tarachidicAcid_mg\tarachidonicAcid_mg\targinine_mg\tash_g\tasparticAcid_mg\tbatacarotene_micro_g\tbegenicAcid_mg\tbiotin_micro_g\tbutyricAcid_mg\tcalcium_mg\tcapricAcid_mg\tcaproicAcid_mg\tcaprylicAcid_mg\tcarbohydrate_g\tcholecalciferol_micro_g\tcholesterol_mg\tcopper_mg\tcysteine_mg\tdihomolinolenicAcid_mg\tdocosadienoicAcid_mg\tdocosahexaenoicAcid_mg\tdocosapentaenoicAcid_mg\teicosadienoicAcid_mg\teicosapentaenoicAcid_mg\teicosatrienoicAcid_mg\tenergy_kcal\tergocalciferol_micro_g\terucicAcid_mg\tfat_g\tfolate_micro_g\tfolicAcid_micro_g\tfoodFolate_micro_g\tfructose_g\tgadoleicAcid_mg\tgalactose_g\tgammalinolenicAcid_mg\tglucose_g\tglutamicAcid_mg\tglycine_mg\thenicosanoicAcid_mg\theptadecanoicAcid_mg\theptadecenoicAcid_mg\thistidine_mg\tindex\tiodine_micro_g\tiron_mg\tisoleucine_mg\tlactose_g\tlauricAcid_mg\tleucine_mg\tlignocericAcid_mg\tlinoleicAcid_mg\tlysine_mg\tmagnesium_mg\tmaltose_g\tmanganese_mg\tmenaquinone_micro_g\tmethionine_mg\tmolybdenum_micro_g\tmyristicAcid_mg\tmyristoleicAcid_mg\tnervonicAcid_mg\tniacin_mg\tnicotinamide_mg\tnicotinicAcid_mg\toleicAcid_mg\tpalmiticAcid_mg\tpalmitoleicAcid_mg\tpantothenicAcid_mg\tpentadecanoicAcid_mg\tphenylalanine_mg\tphosphorus_mg\tphylloquinone_micro_g\tpotassium_mg\tproline_mg\tprotein_g\tpyridoxine_mg\trefuse_percent\tretinol_micro_g\triboflavin_mg\tsaltEquivalent_g\tselenium_micro_g\tserine_mg\tsodium_mg\tstearicAcid_mg\tsucrose_g\ttaurine_mg\tthiamin_mg\tthreonine_mg\ttocpha_mg\ttocphb_mg\ttocphd_mg\ttocphg_mg\ttoctra_mg\ttoctrb_mg\ttoctrd_mg\ttoctrg_mg\ttotalAminoAcids_mg\ttotalDietaryFiber_g\ttotalEssentialAminoAcids_mg\ttotalEssentialFattyAcids_g\ttotalFattyAcids_g\ttotalMonounsaturatedFattyAcids_g\ttotalN_3PolyunsaturatedFattyAcids_g\ttotalN_6PolyunsaturatedFattyAcids_g\ttotalNiacinEquivalents_mg\ttotalPolyunsaturatedFattyAcids_g\ttotalSaturatedFattyAcids_g\ttotalSugars_g\ttotalTransFattyAcids_g\ttotalUnsaturatedFattyAcids_g\ttrans_linoleicAcids_mg\ttrans_linolenicAcids_mg\ttrans_oleicAcids_mg\ttricosanoicAcid_mg\ttridecanoicAcid_mg\ttryptophan_mg\ttyrosine_mg\tvaccenicAcid_mg\tvaline_mg\tvitaminA_micro_g\tvitaminB12_micro_g\tvitaminB6_mg\tvitaminC_mg\tvitaminD_micro_g\tvitaminE_mg\tvitaminK_micro_g\twaterInsolubleDietaryFiber_g\twaterSolubleDietaryFiber_g\twater_g\tzinc_mg";
        String fieldNames[] = fieldName.split("\\t");

        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("NationalDB_v3"); //get the name of the collection that you want

        MongoCursor<Document> cursor =  robosCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol
        int count = 0;

        while(cursor.hasNext()) {
            int modifeidCnt = 0;
            Document doc = cursor.next();
            Object id = doc.get("_id");

            for (String name: fieldNames) {
                Object value = doc.get(name);
                if(value instanceof String) {
                    String valueToString = (String)value;
                    double doubleValue;
                    if(valueToString.contains(",") )
                        valueToString = valueToString.replaceAll(",", "");

                    if(valueToString.contains("Tr") || (valueToString.contains("tr")))
                        valueToString = "0";
                    else if(valueToString.contains("("))
                        valueToString = valueToString.substring(1,valueToString.length() -1);

                    doubleValue = Double.parseDouble(valueToString);

                    UpdateResult updateResult = robosCollection.updateMany(Filters.eq("_id", id), Updates.set(name, doubleValue));
                    modifeidCnt += updateResult.getModifiedCount();
                }
            }
            log.info("count: {} finished,change: {}", ++count, modifeidCnt);
        }
    }

    public void convertIngredientIntegerTypeToDouble(){
        String fieldName = "alanine_mg\talpalinolenicAcid_mg\tarachidicAcid_mg\tarachidonicAcid_mg\targinine_mg\tash_g\tasparticAcid_mg\tbatacarotene_micro_g\tbegenicAcid_mg\tbiotin_micro_g\tbutyricAcid_mg\tcalcium_mg\tcapricAcid_mg\tcaproicAcid_mg\tcaprylicAcid_mg\tcarbohydrate_g\tcholecalciferol_micro_g\tcholesterol_mg\tcopper_mg\tcysteine_mg\tdihomolinolenicAcid_mg\tdocosadienoicAcid_mg\tdocosahexaenoicAcid_mg\tdocosapentaenoicAcid_mg\teicosadienoicAcid_mg\teicosapentaenoicAcid_mg\teicosatrienoicAcid_mg\tenergy_kcal\tergocalciferol_micro_g\terucicAcid_mg\tfat_g\tfolate_micro_g\tfolicAcid_micro_g\tfoodFolate_micro_g\tfructose_g\tgadoleicAcid_mg\tgalactose_g\tgammalinolenicAcid_mg\tglucose_g\tglutamicAcid_mg\tglycine_mg\thenicosanoicAcid_mg\theptadecanoicAcid_mg\theptadecenoicAcid_mg\thistidine_mg\tindex\tiodine_micro_g\tiron_mg\tisoleucine_mg\tlactose_g\tlauricAcid_mg\tleucine_mg\tlignocericAcid_mg\tlinoleicAcid_mg\tlysine_mg\tmagnesium_mg\tmaltose_g\tmanganese_mg\tmenaquinone_micro_g\tmethionine_mg\tmolybdenum_micro_g\tmyristicAcid_mg\tmyristoleicAcid_mg\tnervonicAcid_mg\tniacin_mg\tnicotinamide_mg\tnicotinicAcid_mg\toleicAcid_mg\tpalmiticAcid_mg\tpalmitoleicAcid_mg\tpantothenicAcid_mg\tpentadecanoicAcid_mg\tphenylalanine_mg\tphosphorus_mg\tphylloquinone_micro_g\tpotassium_mg\tproline_mg\tprotein_g\tpyridoxine_mg\trefuse_percent\tretinol_micro_g\triboflavin_mg\tsaltEquivalent_g\tselenium_micro_g\tserine_mg\tsodium_mg\tstearicAcid_mg\tsucrose_g\ttaurine_mg\tthiamin_mg\tthreonine_mg\ttocpha_mg\ttocphb_mg\ttocphd_mg\ttocphg_mg\ttoctra_mg\ttoctrb_mg\ttoctrd_mg\ttoctrg_mg\ttotalAminoAcids_mg\ttotalDietaryFiber_g\ttotalEssentialAminoAcids_mg\ttotalEssentialFattyAcids_g\ttotalFattyAcids_g\ttotalMonounsaturatedFattyAcids_g\ttotalN_3PolyunsaturatedFattyAcids_g\ttotalN_6PolyunsaturatedFattyAcids_g\ttotalNiacinEquivalents_mg\ttotalPolyunsaturatedFattyAcids_g\ttotalSaturatedFattyAcids_g\ttotalSugars_g\ttotalTransFattyAcids_g\ttotalUnsaturatedFattyAcids_g\ttrans_linoleicAcids_mg\ttrans_linolenicAcids_mg\ttrans_oleicAcids_mg\ttricosanoicAcid_mg\ttridecanoicAcid_mg\ttryptophan_mg\ttyrosine_mg\tvaccenicAcid_mg\tvaline_mg\tvitaminA_micro_g\tvitaminB12_micro_g\tvitaminB6_mg\tvitaminC_mg\tvitaminD_micro_g\tvitaminE_mg\tvitaminK_micro_g\twaterInsolubleDietaryFiber_g\twaterSolubleDietaryFiber_g\twater_g\tzinc_mg";
        String fieldNames[] = fieldName.split("\\t");

        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> robosCollection = mongoDb.getCollection("NationalDB_v3"); //get the name of the collection that you want

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
//doc.getDouble();
        String result = "";
        /*
        int idx = 0;

        for(; idx< split.length;++idx){
            String full = "double " + split[idx] + " = doc.getDouble(\""+ split[idx] +"\");" ;
            result = result + full + "\n";
        }
*/


        int idx = 0;

        for(; idx< split.length;++idx){
            String full =  split[idx] + " , " ;
            result = result + full ;
        }

        /*


        int idx = 0;

        for(; idx< split.length;++idx){
            String column = prefix + (idx + 6);
            String full = "\"" + column + "\" : \"" + split[idx] + "\"" ;
            result = result + full + " , ";
        }
        */
        log.info(result);

    }
/*
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

    } */

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
/*
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


            //DetailIngredient ingredient = new DetailIngredient(name, dataCode, dataTypeName, representationName, originTypeName, largeCategory, mediumCategory, smallCategory, subCategory,component);
            //ingredientRepository.save(ingredient);
        }
        //cursor.forEachRemaining(System.out::println);
    }*/

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
