package com.example.android.bakingappudacity.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;

import java.util.List;

public class RecipesRepository {
    private RecipesDao mRecipesDao;
    private LiveData<List<RecipesModel>> mAllRecipes;

    public RecipesRepository(Application application) {
        RecipesRoomDatabase database = RecipesRoomDatabase.getDatabase(application);
        this.mRecipesDao = database.recipesDao();
        this.mAllRecipes = mRecipesDao.getAll();
    }

    public LiveData<List<RecipesModel>> getmAllRecipes() {
        return mAllRecipes;
    }

    public void insert (final List<RecipesModel> recipesModel) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
             mRecipesDao.insert(recipesModel);
            }
        });
        thread.start();
    }

    public void deleteAll(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mRecipesDao.DeleteAll();
            }
        });
        thread.start();
    }
}
