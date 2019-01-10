package com.example.android.bakingappudacity.RecipesWidget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingappudacity.R;
import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class GridIntentService extends IntentService {
    private String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private ArrayList<RecipesModel> mData;
    static Context con;
    public RecipesModel model;
    ArrayList<Ingredient> ings;

    public GridIntentService() {
        super("GridIntentService");
    }

    public static void InflateGrid(Context context){
        Intent intent = new Intent(context,GridIntentService.class);
        con=context;
        intent.setAction(con.getString(R.string.InflateGrid));
        context.startService(intent);
    }

    public static void InflateList(Context context, int viewIndex) {
        Intent intent = new Intent(context,GridIntentService.class);
        con = context;
        intent.setAction(con.getString(R.string.InflateList));
        intent.putExtra(con.getString(R.string.viewIndex),viewIndex);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            if (intent.getAction().equals(con.getString(R.string.InflateGrid))) {
                getGridData();
            }
            if (intent.getAction().equals(con.getString(R.string.InflateList))){
                int index = intent.getIntExtra(con.getString(R.string.viewIndex),0);
                getListData(index);
            }
        }
    }

    private void getListData(final int index) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                RecipesModel[] recipesModels = gson.fromJson(response, RecipesModel[].class);
                mData = new ArrayList<>(Arrays.asList(recipesModels));
                model = mData.get(index);
                ings = model.getIngredients();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(con);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(con, IngredientWidgetProvider.class));
            //    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
                IngredientWidgetProvider.update(con,appWidgetManager,appWidgetIds,mData,ings);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.e("error","volley list error");

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getGridData() {

                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                RecipesModel[] recipesModels = gson.fromJson(response, RecipesModel[].class);
                mData = new ArrayList<>(Arrays.asList(recipesModels));
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(con);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(con, IngredientWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
                IngredientWidgetProvider.update(con,appWidgetManager,appWidgetIds,mData,ings);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error","volley error");

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }
}
