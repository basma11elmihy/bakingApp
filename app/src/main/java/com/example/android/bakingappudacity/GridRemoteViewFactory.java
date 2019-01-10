package com.example.android.bakingappudacity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private ArrayList<RecipesModel> mData;
    private Intent intent;

    public GridRemoteViewFactory(Context context,Intent intent) {
        this.context = context;
        this.intent = intent;

    }

    public void initData(){
        String stringData = String.valueOf(intent.getData().getSchemeSpecificPart());
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        RecipesModel[] recipesModels = gson.fromJson(stringData, RecipesModel[].class);
        mData = new ArrayList<>(Arrays.asList(recipesModels));

    }
    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
       initData();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mData == null || mData.size()==0)
            return 0;
        else
            return mData.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mData == null || mData.size() == 0) return null;
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_grid_list_item);
        RecipesModel current = mData.get(position);

       String name = current.getName();
        views.setTextViewText(R.id.label_grid,name);

        Bundle extras = new Bundle();
        extras.putInt("extra_id_grid", position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
       views.setOnClickFillInIntent(R.id.label_grid, fillInIntent);

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
