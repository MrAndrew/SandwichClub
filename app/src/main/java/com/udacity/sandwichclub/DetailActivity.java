package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.utils.RoundCornersBorderTransform;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Renamed to reflect appropriate name
        ImageView sandwichIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.e(TAG, "Extra position not found in detail intent");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        //try and catch block in case parse method throws an exception
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.e(TAG, "Problem parsing sandwich from json");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        //reformatted to follow current guidelines from "square.github.io/picasso/"
        //and created a custom transformation to customize styling
        Picasso.get()
                .load(sandwich.getImage())
                //in case a future URL link doesn't work, currently I changed the url for the
                //'SHAWARMA' to a working link (provided one didn't appear to load)
                .error(R.drawable.no_image_found)
                .transform(new RoundCornersBorderTransform(350, 1))
                .resize(400, 250)
                .centerCrop()
                .into(sandwichIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //find views
        TextView akaView = findViewById(R.id.also_known_tv);
        TextView originView = findViewById(R.id.origin_tv);
        TextView ingredientsView = findViewById(R.id.ingredients_tv);
        TextView descriptionView = findViewById(R.id.description_tv);
        //set values to views from object
        originView.setText(getString(R.string.place_of_origin_content, sandwich.getPlaceOfOrigin()));
        descriptionView.setText(getString(R.string.descritption_conent, sandwich.getDescription()));
        //setting values in the array to the views
        List<String> ingredientsArray = sandwich.getIngredients();
        // "\n" is for layout consistency when missing content
        if(ingredientsArray.size() <= 0) { ingredientsView.setText("\n"); }
        for (int i = 0 ; i < ingredientsArray.size(); i++) {
            ingredientsView.append(ingredientsArray.get(i));
            ingredientsView.append("\n");
        }
        List<String> akaArray = sandwich.getAlsoKnownAs();
        // "\n" is for layout consistency when missing content
        if(akaArray.size() <= 0) { akaView.setText("\n"); }
        for (int i = 0 ; i < akaArray.size(); i++) {
            akaView.append(akaArray.get(i));
            akaView.append("\n");
        }

    }

}
