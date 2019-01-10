package com.example.android.bakingappudacity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingappudacity.Adapters.RecipeIngsDetailsAdapter;
import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private RecipeIngsDetailsAdapter mAdapter;
    private NestedScrollView nestedScrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ArrayList<Ingredient> ings = getActivity().getIntent().getParcelableArrayListExtra(getContext().getString(R.string.ings));

        mRecyclerView = view.findViewById(R.id.ingRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecipeIngsDetailsAdapter(getContext(),ings);
        mRecyclerView.setAdapter(mAdapter);

        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollViewIng);
        nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                ((NestedScrollView) view.findViewById(R.id.nestedScrollViewIng)).fullScroll(View.FOCUS_UP);
            }
        });
        return view;
    }

}
