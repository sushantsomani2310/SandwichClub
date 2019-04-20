package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String sandwichJsonString) {
        try{
            JSONObject sandwichJson = new JSONObject(sandwichJsonString);
            String mainName = sandwichJson.getJSONObject("name").get("mainName").toString();
            int sandwichNameCount = (sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs")).length();
            List<String> alsoKnownAs = new ArrayList<>();
            for(int i=0;i<sandwichNameCount;i++){
                alsoKnownAs.add(sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs").get(i).toString());
            }
            String placeOfOrigin = sandwichJson.get("placeOfOrigin").toString();
            String description = sandwichJson.get("description").toString();
            String image = sandwichJson.get("image").toString();
            int ingredientsCount = sandwichJson.getJSONArray("ingredients").length();
            List<String> ingredients = new ArrayList<>();
            for(int j=0;j<ingredientsCount;j++){
                ingredients.add(sandwichJson.getJSONArray("ingredients").get(j).toString());
            }
            return new Sandwich( mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch (Exception ex){
            return null;
        }
    }
}
