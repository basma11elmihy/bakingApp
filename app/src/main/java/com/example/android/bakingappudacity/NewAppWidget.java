//package com.example.android.bakingappudacity;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.RemoteViews;
//
///**
// * Implementation of App Widget functionality.
// */
//public class NewAppWidget extends AppWidgetProvider {
//
//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
////        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
////        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
//        // Construct the RemoteViews object
////        RemoteViews views;
////        views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
////        if (width < 300){
//
////            Intent intent = new Intent(context,MainActivity.class);
////            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
////            views.setOnClickPendingIntent(R.id.appwidget_btn,pendingIntent);
////        }else{
////            views = getGridView(context);
////            Log.v("In remoteView","I am in remoteview");
////        }
////        appWidgetManager.updateAppWidget(appWidgetId, views);
//        // Instruct the widget manager to update the widget
////        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_IngText);
//
//    }
//
//    private static RemoteViews getGridView(Context context) {
////        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_ings_layout);
////
////        Intent intent = new Intent(context,ListWidgetService.class);
////        views.setRemoteAdapter(R.id.widget_IngText,intent);
////        Log.v("intent","intent Sent");
//
////        Intent appIntent = new Intent(context,RecipeIngredientsDetails.class);
////        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
////        views.setPendingIntentTemplate(R.id.widget_grid_view,pendingIntent);
//
//
//        return null;
//    }
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        // There may be multiple widgets active, so update all of them
////        for (int appWidgetId : appWidgetIds) {
////            updateAppWidget(context, appWidgetManager, appWidgetId);
////        }
//    }
//
//    @Override
//    public void onEnabled(Context context) {
//        // Enter relevant functionality for when the first widget is created
//    }
//
//    @Override
//    public void onDisabled(Context context) {
//        // Enter relevant functionality for when the last widget is disabled
//    }
//}
//
