package com.example.android.bakingappudacity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingappudacity.R;
import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;

import java.util.ArrayList;

public class RecipeIngsDetailsAdapter extends RecyclerView.Adapter<RecipeIngsDetailsAdapter.IngsViewHolder>  {

    private Context context;
    private ArrayList<Ingredient> mData;

    public RecipeIngsDetailsAdapter(Context context, ArrayList<Ingredient> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public IngsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ings_list_item,viewGroup,false);
        return new IngsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngsViewHolder ingsViewHolder, int i) {
        Ingredient currentIngredient = mData.get(i);
        double quantity = currentIngredient.getQuantity();
        String meausure = currentIngredient.getMeasure();
        String ings = currentIngredient.getIngredient();
        String text = quantity + " " + meausure + " " + ings;
        ingsViewHolder.ings.setText(text);

    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0)
        return 0;
        else
            return mData.size();
    }

    public class IngsViewHolder extends RecyclerView.ViewHolder {
        private TextView ings;
        public IngsViewHolder(@NonNull View itemView) {
            super(itemView);
            ings = itemView.findViewById(R.id.ingsText);
        }
    }
}
