package com.example.android.bakingappudacity;

import android.app.Application;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {
    public static int viewIndex = 0;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int Height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        RemoteViews views;

        if (Height<300) {
            views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
            Intent intent = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        }
        else{
            views = getIngredientsList(context,appWidgetId);

        }
       appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_list_view);
      //  appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_grid_view);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    private static RemoteViews getIngredientsList(Context context,int appWidgetId) {
//        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_ings_layout);
//        Intent intent = new Intent(context,ListWidgetService.class);
//        views.setRemoteAdapter(R.id.widget_list_view,intent);
//        context.startService(intent);
//        return views;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_ings_layout);
        Intent intent = new Intent(context,GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view,intent);
    //    context.startService(intent);
//        Intent ListIntent = new Intent(context,ListWidgetService.class);
//        views.setRemoteAdapter(R.id.widget_list_view,ListIntent);
//        context.startService(ListIntent);


            if (viewIndex != 0) {
                appWidgetManager.updateAppWidget(appWidgetId,views);
                Intent ListIntent = new Intent(context,ListWidgetService.class);
                ListIntent.putExtra("extra_id", viewIndex);
                views.setRemoteAdapter(R.id.widget_list_view, ListIntent);

                //context.startService(ListIntent);
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientWidgetProvider.class));
//                update(context, appWidgetManager,appWidgetIds);

            }
        Intent appIntent = new Intent(context, IngredientWidgetProvider.class);
        appIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        appIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent appPendingIntent = PendingIntent.getBroadcast(context, 0,
                appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);



        return views;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            viewIndex = intent.getIntExtra("extra_id_grid",0);
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            Toast.makeText(context,"position "+viewIndex,Toast.LENGTH_LONG).show();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            updateAppWidget(context,appWidgetManager,appWidgetId);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_list_view);

        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        update(context,appWidgetManager,appWidgetIds);
    }
    public static void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

          //  Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
         //   onAppWidgetOptionsChanged(context,appWidgetManager,appWidgetId,options);
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

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

//      appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_list_view);
//       appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_grid_view);
        updateAppWidget(context,appWidgetManager,appWidgetId);

//         int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientWidgetProvider.class));
//            update(context, appWidgetManager,appWidgetIds);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }


}

