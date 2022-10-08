package com.fridgerescuer.springboot.databaseoperation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AutoCompleteInjector {
    @Autowired
    private MongoClient mongoClient;

    Map<String, PriorityQueue<SimpleRecipe>> prefixCacheMap = new HashMap<>();
    int count =0;

    public void generateAllAutoCompleteCache(){
        List<SimpleRecipe> allRecipes = getAllRecipes();

        for (SimpleRecipe recipe: allRecipes ) {
            String[] splittedNamed = recipe.getName().split(" ");
            splittedNamed[0] = recipe.getName();    //맨처음은 fullName

            List<String> parsedName = new ArrayList<>(Arrays.asList(splittedNamed));

            insertKeywordByStrings(parsedName, recipe);
        }

        Set<String> keySet = prefixCacheMap.keySet();
        log.info("keySet size ={}", keySet.size());
        Iterator<String> iterator = keySet.iterator();
        iterator.forEachRemaining((key) -> {putAutoCompleteCache(key);});

    }

    @CachePut(cacheNames = "recipeAutoComplete", key = "#p0")
    private List<String> putAutoCompleteCache(String keyword){
        List<String> recipeIds = new ArrayList<>();

         prefixCacheMap.get(keyword).iterator()
                 .forEachRemaining((simpleRecipe) -> {
                     log.info("keyword: {}     =>    recipe ={}" ,keyword, simpleRecipe.getName());
                     recipeIds.add(simpleRecipe.getId());});
        log.info("complete={}", count++);
         return recipeIds;
    }

    private void insertKeywordByStrings(List<String> parsedName, SimpleRecipe recipe){

        for (String name: parsedName) {
            for (int i = 1; i <= name.length(); i++) {
                String substring = name.substring(0, i);
                if(!prefixCacheMap.containsKey(substring))
                    prefixCacheMap.put(substring,new PriorityQueue<SimpleRecipe>());

                PriorityQueue<SimpleRecipe> pq = prefixCacheMap.get(substring);

                if(pq.size()>= 6){    //항상 6개 이하로 유지
                    SimpleRecipe peek = pq.peek();
                    if(peek.getCachePriority() < recipe.getCachePriority()){
                        pq.poll();
                        pq.add(recipe);
                    }
                    return;
                }

                pq.add(recipe);
            }
        }
    }

    private List<SimpleRecipe> getAllRecipes(){
        MongoDatabase mongoDb = mongoClient.getDatabase("test");  //get database, where DBname is the name of your database

        MongoCollection<Document> recipeCollection = mongoDb.getCollection("recipe"); //get the name of the collection that you want

        MongoCursor<Document> recipeCursor =  recipeCollection.find().cursor();//Mongo Cursor interface implementing the iterator protocol

        int cnt = 0;
        List<SimpleRecipe> recipes = new ArrayList<>();
        while (recipeCursor.hasNext()){
            Document ingredient = recipeCursor.next();
            String fullName = ingredient.getString("name");
            String id = ingredient.get("_id").toString();
            int priority = ingredient.getInteger("cachePriority");

            recipes.add(new SimpleRecipe(fullName,id,priority));

            //if(cnt++ >4) break;
            //log.info( "pass: ",cnt++);
        }
        return recipes;
    }


    @Getter
    @AllArgsConstructor
    private class SimpleRecipe implements Comparable<SimpleRecipe>{
        private String name;
        private String id;
        private int cachePriority;

        @Override
        public int compareTo(SimpleRecipe o) {
            if (this.cachePriority > o.getCachePriority())
                return 1;
            else if(this.cachePriority < o.getCachePriority())
                return -1;
            return 0;
        }
    }
}
