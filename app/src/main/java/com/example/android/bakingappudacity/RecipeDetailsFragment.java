package com.example.android.bakingappudacity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.bakingappudacity.Adapters.DetailsAdapter;
import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.example.android.bakingappudacity.RecipesModelJson.Step;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment implements DetailsAdapter.MyListItemClickListener {
    private RecyclerView recyclerView;
    private DetailsAdapter adapter;
    private NestedScrollView nestedScrollView;
    private Button IngButton;
    private Button StepButton;
    private boolean IsmasterDetail;
    private ArrayList<Step> steps;
    private String name;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        recyclerView = rootview.findViewById(R.id.detailsRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //for help with nested scroll view focus:
        //https://stackoverflow.com/questions/14801215/scrollview-not-scrolling-down-completely/14897780#14897780
        nestedScrollView = (NestedScrollView) rootview.findViewById(R.id.nestedScrollView);
                nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                ((NestedScrollView) rootview.findViewById(R.id.nestedScrollView)).fullScroll(View.FOCUS_UP);
            }
        });


        RecipesModel model = getActivity().getIntent().getParcelableExtra("parcel_data");
        name = model.getName();


        steps = getActivity().getIntent().getParcelableArrayListExtra("steps");
        final ArrayList<Ingredient> ings = getActivity().getIntent().getParcelableArrayListExtra("ings");
        adapter = new DetailsAdapter(getContext(),steps,this);
        recyclerView.setAdapter(adapter);
        StepButton = rootview.findViewById(R.id.StepDescriptionBtn);
        IngButton = (Button) rootview.findViewById(R.id.IngBtn);
        if (rootview.findViewById(R.id.viewDivider) != null){
            IsmasterDetail = true;
        }
        if (!IsmasterDetail) {
            IngButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), RecipeIngredientsDetails.class);
                    intent.putParcelableArrayListExtra("ings", ings);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
            });
        }
        else {
            IngButton.setBackground(getResources().getDrawable(R.drawable.touch_selector));
            IngButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.masterDetailFrag,new RecipeIngredientsFrag())
                            .commit();
                }
            });

        }

        return rootview;
    }

    @Override
    public void onItemClick(int position) {
        Step currentStep = steps.get(position);
        if (IsmasterDetail){
            getFragmentManager().beginTransaction()
                    .replace(R.id.masterDetailFrag,new RecipeStepsDetailsFrag(currentStep))
                    .commit();

        }
        else {

            Intent intent = new Intent(getContext(),RecipeStepsDetails.class);
            intent.putExtra("step_extra",currentStep);
            intent.putExtra("name",name);
            intent.putParcelableArrayListExtra("steps_list",steps);
            startActivity(intent);
        }
    }
}
