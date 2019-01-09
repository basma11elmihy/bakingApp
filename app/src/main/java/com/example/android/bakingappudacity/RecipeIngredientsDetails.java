package com.example.android.bakingappudacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeIngredientsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.IngredientsContainer,new RecipeIngredientsFrag())
                .commit();
    }
}
