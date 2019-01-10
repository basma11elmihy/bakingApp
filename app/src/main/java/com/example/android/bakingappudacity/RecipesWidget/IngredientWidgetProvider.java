package com.example.android.bakingappudacity.RecipesWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingappudacity.R;
import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
//https://stackoverflow.com/questions/46400576/remoteviewfactory-ondatasetchanged-only-called-once-per-notifyappwidgetviewdat
public class IngredientWidgetProvider extends AppWidgetProvider {
    public static int viewIndex = -1;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, ArrayList<RecipesModel> mData,ArrayList<Ingredient> ings) {

        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_ings_layout);

        if (viewIndex >= 0) {
            Intent ListIntent = new Intent(context, ListWidgetService.class);
            String ListDumb = new Gson().toJson(ings);
            ListIntent.setData(Uri.fromParts("scheme",ListDumb,null));
            views.setRemoteAdapter(R.id.widget_list_view, ListIntent);
            viewIndex = -1;
        }
        else {
            views = getIngredientsList(context, mData);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    private static RemoteViews getIngredientsList(Context context,ArrayList<RecipesModel> mData) {
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_ings_layout);
        Intent intent = new Intent(context,GridWidgetService.class);
        String dumb = new Gson().toJson(mData);
        intent.setData(Uri.fromParts("scheme",dumb,null));
        views.setRemoteAdapter(R.id.widget_grid_view,intent);

        Intent appIntent = new Intent(context, IngredientWidgetProvider.class);
        appIntent.setAction("action");
        PendingIntent appPendingIntent = PendingIntent.getBroadcast(context, 0,
                appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        return views;

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("action")){

            viewIndex = intent.getIntExtra("extra_id_grid",0);
            Toast.makeText(context,"position "+viewIndex,Toast.LENGTH_LONG).show();
            GridIntentService.InflateList(context,viewIndex);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            GridIntentService.InflateGrid(context);
        }

    }

    public static void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, ArrayList<RecipesModel> mData, ArrayList<Ingredient> ings){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,mData,ings);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

