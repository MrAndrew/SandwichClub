package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //create new Sandwich object
        Sandwich sandwich = new Sandwich();

        //get and set sandwich JSON objects from json string
        JSONObject sandwichJsonObj = new JSONObject(json);
        JSONObject sandwhichNames = sandwichJsonObj.getJSONObject("name");

        //set sandwich object properties by getting values from JSONObjects
        sandwich.setMainName(sandwhichNames.getString("mainName"));
        sandwich.setPlaceOfOrigin(sandwichJsonObj.getString("placeOfOrigin"));
        sandwich.setDescription(sandwichJsonObj.getString("description"));
        sandwich.setImage(sandwichJsonObj.getString("image"));

        //converts JSON Array to an ArrayList to add aka names to the Sandwich object
        ArrayList<String> akaList = new ArrayList<String>();
        JSONArray akaArray = (JSONArray)sandwhichNames.getJSONArray("alsoKnownAs");
        if (akaArray != null) {
            for (int i = 0; i < akaArray.length(); i++) {
                akaList.add(akaArray.getString(i));
            }
        }
        sandwich.setAlsoKnownAs(akaList);

        //converts JSON Array to an ArrayList to add ingredients to the Sandwich object
        ArrayList<String> ingredientsList = new ArrayList<String>();
        JSONArray ingredientsArray = (JSONArray)sandwichJsonObj.getJSONArray("ingredients");
        if (ingredientsArray != null) {
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }
        }
        sandwich.setIngredients(ingredientsList);

        //returns the sandwich object set above
        return sandwich;
    }

}
