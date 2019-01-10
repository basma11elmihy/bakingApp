package com.example.android.bakingappudacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.android.bakingappudacity.R;
import com.example.android.bakingappudacity.RecipeDetails;
import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.example.android.bakingappudacity.RecipesModelJson.Step;

import java.util.ArrayList;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder> {
    private Context context;
    private ArrayList<RecipesModel> mData;

    public MainMenuAdapter(Context context, ArrayList<RecipesModel> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_recipes_list_item_names,viewGroup,false);
        MainMenuViewHolder viewHolder = new MainMenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainMenuViewHolder mainMenuViewHolder, int i) {
        final RecipesModel model = mData.get(i);
        mainMenuViewHolder.mainMenuButton.setText(model.getName());
        mainMenuViewHolder.mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"GO",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,RecipeDetails.class);

                ArrayList<Step> step = model.getSteps();
                ArrayList<Ingredient> ingredients = model.getIngredients();
                intent.putExtra("parcel_data",model);
                intent.putParcelableArrayListExtra("steps",step);
                intent.putParcelableArrayListExtra("ings",ingredients);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0)
        return 0;
        else
            return mData.size();
    }

    public class MainMenuViewHolder extends RecyclerView.ViewHolder{
        private Button mainMenuButton;

        public MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mainMenuButton = itemView.findViewById(R.id.main_menu_btn);
        }

    }
}
