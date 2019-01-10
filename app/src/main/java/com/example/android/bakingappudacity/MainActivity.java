package com.example.android.bakingappudacity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingappudacity.Adapters.MainMenuAdapter;
import com.example.android.bakingappudacity.Database.RecipesViewModel;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private RecyclerView mRecyclerView;
    private MainMenuAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private RecipesViewModel viewModel;
    private ArrayList<RecipesModel> mData;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


            mRecyclerView = findViewById(R.id.recipes_recycler_view);
            if (getResources().getBoolean(R.bool.isTablet)) {
                layoutManager = new GridLayoutManager(this, 3);
            } else {
                layoutManager = new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false);
            }
            mRecyclerView.setLayoutManager(layoutManager);

            viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        getIdlingResource();


        if (mIdlingResource != null){
            mIdlingResource.setIdleState(false);
        }
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    RecipesModel[] recipesModels = gson.fromJson(response, RecipesModel[].class);
                    mData = new ArrayList<>(Arrays.asList(recipesModels));
                    mAdapter = new MainMenuAdapter(context, mData);
                    mRecyclerView.setAdapter(mAdapter);
                    viewModel.insert(mData);
                    if (mIdlingResource != null){
                        mIdlingResource.setIdleState(true);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("mainActivity","vollry error");

                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);


    }
}
