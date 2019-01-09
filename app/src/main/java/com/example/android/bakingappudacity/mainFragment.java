package com.example.android.bakingappudacity;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

//help using Volley and GSON from:
//https://www.youtube.com/watch?v=3n1Jcr1vOhA&fbclid=IwAR1F-fMC3kGX1TAG018ZwrgG4pcj79Iwx5ssrP3LqDMACviKBso8PcimSq4
//help using room
//https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..index#0
public class mainFragment extends Fragment {
    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private RecyclerView mRecyclerView;
    private MainMenuAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private RecipesViewModel viewModel;
    private ListRemoteViewFactory remoteViewFactory;
    LiveData<List<RecipesModel>> mLiveData;
    private ArrayList<RecipesModel> mData;


    public mainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = view.findViewById(R.id.recipesRV);
        if (getResources().getBoolean(R.bool.isTablet)) {
            layoutManager = new GridLayoutManager(getContext(), 3);
        } else {
            layoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
        }
        mRecyclerView.setLayoutManager(layoutManager);

        viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);


        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getContext(), "OK", Toast.LENGTH_LONG).show();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                RecipesModel[] recipesModels = gson.fromJson(response, RecipesModel[].class);
                mData = new ArrayList<>(Arrays.asList(recipesModels));
                mAdapter = new MainMenuAdapter(getContext(), mData);
                mRecyclerView.setAdapter(mAdapter);
                viewModel.insert(mData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
        viewModel.getmAll().observe(this, new Observer<List<RecipesModel>>() {
            @Override
            public void onChanged(@Nullable List<RecipesModel> recipesModels) {

            }
        });

        return view;
    }


}

