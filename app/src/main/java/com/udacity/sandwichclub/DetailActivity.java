package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView descriptionTextView,originTextView,ingredientsTextView,alsoKnownAsTextView;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //initialize all Views
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        descriptionTextView = (TextView)findViewById(R.id.description_tv);
        originTextView = (TextView)findViewById(R.id.origin_tv);
        ingredientsTextView = (TextView)findViewById(R.id.ingredients_tv);
        alsoKnownAsTextView = (TextView)findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        String noDataAvailable = "Info not available\n\n\n";
        if(!sandwich.getDescription().isEmpty()) descriptionTextView.setText(sandwich.getDescription()+"\n\n\n");
        else descriptionTextView.setText(noDataAvailable);

        if(!sandwich.getPlaceOfOrigin().isEmpty()) originTextView.setText(sandwich.getPlaceOfOrigin()+"\n\n\n");
        else originTextView.setText(noDataAvailable);

        int ingredientsCount = sandwich.getIngredients().size();
        if(ingredientsCount==0) ingredientsTextView.setText(noDataAvailable);
        for(int i=0;i<ingredientsCount;i++){
            if(i!=(ingredientsCount-1)) ingredientsTextView.append(sandwich.getIngredients().get(i)+", ");
            else ingredientsTextView.append(sandwich.getIngredients().get(i)+"\n\n\n");
        }

        int otherNamesCount = sandwich.getAlsoKnownAs().size();
        if(otherNamesCount==0) alsoKnownAsTextView.setText(noDataAvailable);
        for(int i=0;i<otherNamesCount;i++){
            if(i!=(otherNamesCount-1)) alsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i)+", ");
            else alsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i)+"\n\n\n");
        }
    }
}
