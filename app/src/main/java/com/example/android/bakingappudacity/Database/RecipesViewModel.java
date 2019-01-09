package com.example.android.bakingappudacity.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private RecipesRepository mRepo;
    private LiveData<List<RecipesModel>> mAll;
    public RecipesViewModel(@NonNull Application application) {
        super(application);
        mRepo = new RecipesRepository(application);
        mAll = mRepo.getmAllRecipes();
    }

    public LiveData<List<RecipesModel>> getmAll() {
        return mAll;
    }
    public void insert(List<RecipesModel> recipesModel){
        mRepo.insert(recipesModel);
    }
    public void deleteAll(){
        mRepo.deleteAll();
    }
}
