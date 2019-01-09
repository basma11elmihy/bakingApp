package com.example.android.bakingappudacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.bakingappudacity.R;
import com.example.android.bakingappudacity.RecipeStepsDetails;
import com.example.android.bakingappudacity.RecipesModelJson.Step;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    Context context;
    ArrayList<Step> mData;
    MyListItemClickListener MyclickListener;

    public DetailsAdapter(Context context, ArrayList<Step> mData, MyListItemClickListener clickListener) {
        this.context = context;
        this.mData = mData;
        this.MyclickListener = clickListener;
    }
    public interface MyListItemClickListener{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_details_list_item,viewGroup,false);
        DetailsViewHolder viewHolder = new DetailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsViewHolder detailsViewHolder, int i) {
        final Step currentStep = mData.get(i);
        detailsViewHolder.Ingbtn.setText(currentStep.getShortDescription());
        detailsViewHolder.Ingbtn.setBackground(context.getResources().getDrawable(R.drawable.touch_selector));
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0)
        return 0;
        else
            return mData.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Button Ingbtn;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            Ingbtn = itemView.findViewById(R.id.StepDescriptionBtn);
            Ingbtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyclickListener.onItemClick(position);
        }
    }
}
