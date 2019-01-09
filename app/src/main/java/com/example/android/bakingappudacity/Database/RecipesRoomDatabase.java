package com.example.android.bakingappudacity.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.bakingappudacity.RecipesModelJson.Ingredient;
import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;

@Database(entities = {RecipesModel.class,Ingredient.class},version = 1,exportSchema = false)
public abstract class RecipesRoomDatabase extends RoomDatabase {
    public abstract RecipesDao recipesDao();

    private static volatile RecipesRoomDatabase mInstance;

    static RecipesRoomDatabase getDatabase(final Context context){
        if (mInstance == null){
            synchronized (RecipesRoomDatabase.class){
                if (mInstance == null){
                    mInstance = Room.databaseBuilder(context.getApplicationContext()
                            ,RecipesRoomDatabase.class,"roomdatabase")
                            .build();
                }
            }
        }
        return mInstance;
    }

}
