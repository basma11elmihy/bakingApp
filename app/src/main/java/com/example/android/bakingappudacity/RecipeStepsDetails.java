package com.example.android.bakingappudacity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeStepsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_details);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.stepsContainer,new RecipeStepsDetailsFrag())
                .commit();
        String name = getIntent().getStringExtra(this.getString(R.string.name));
        getSupportActionBar().setTitle(name);
    }


}
