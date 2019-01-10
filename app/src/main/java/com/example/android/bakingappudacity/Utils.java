package com.example.android.bakingappudacity;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Utils {

    public Drawable setBackgroundImage(String name, Context context){
        if (name.equals("Nutella Pie")){
            return context.getResources().getDrawable(R.drawable.nutella);
        }
        if (name.equals("Brownies"))
        {
            return context.getResources().getDrawable(R.drawable.brownies);
        }
        if (name.equals("Yellow Cake")){
            return context.getResources().getDrawable(R.drawable.yellow);
        }
        if (name.equals("Cheesecake")){
            return context.getResources().getDrawable(R.drawable.chessecake);
        }
        else
            return context.getResources().getDrawable(R.drawable.bakery);
    }
}
