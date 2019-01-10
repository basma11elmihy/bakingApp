package com.example.android.bakingappudacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;

import java.util.ArrayList;

public class RecipeIngredientsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.IngredientsContainer,new RecipeIngredientsFrag())
                .commit();
        String name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);
    }
}
