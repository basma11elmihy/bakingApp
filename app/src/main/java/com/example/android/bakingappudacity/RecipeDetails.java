package com.example.android.bakingappudacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingappudacity.RecipesModelJson.Step;

public class RecipeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.DetailsContainer,new RecipeDetailsFragment())
                .commit();
    }
}
