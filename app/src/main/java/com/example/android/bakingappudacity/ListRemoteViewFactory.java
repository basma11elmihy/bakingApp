package com.example.android.bakingappudacity;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingappudacity.Adapters.MainMenuAdapter;
import com.example.android.bakingappudacity.Database.RecipesRepository;
import com.example.android.bakingappudacity.Database.RecipesViewModel;
import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/
public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ArrayList<Ingredient> ings;
    private String stringData ;


    public ListRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        this.stringData = String.valueOf(intent.getData().getSchemeSpecificPart());

    }

    @Override
    public void onCreate() {

    }

    @Override
    public  void onDataSetChanged() {
        if (stringData != null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Ingredient[] recipesModels = gson.fromJson(stringData, Ingredient[].class);
            if(recipesModels != null) {
                ings = new ArrayList<>(Arrays.asList(recipesModels));
            }
        }


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ings==null || ings.size() == 0) {
            return 0;
        }
        else
            return ings.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ings == null || ings.size() == 0) return null;
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_list_item);


        Ingredient currentIngredient = ings.get(position);
        double quantity = currentIngredient.getQuantity();
        String meausure = currentIngredient.getMeasure();
        String ing = currentIngredient.getIngredient();
        String text = quantity + " " + meausure + " " + ing;
        views.setTextViewText(R.id.label,text);
        Log.v("InFactory",text);
        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
