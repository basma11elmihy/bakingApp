package com.example.android.bakingappudacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.example.android.bakingappudacity.RecipesModelJson.Step;

public class RecipeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.DetailsContainer,new RecipeDetailsFragment())
                .commit();
        RecipesModel model = getIntent().getParcelableExtra(this.getString(R.string.parcel_data));
        String name = model.getName();
        getSupportActionBar().setTitle(name);
    }
}
