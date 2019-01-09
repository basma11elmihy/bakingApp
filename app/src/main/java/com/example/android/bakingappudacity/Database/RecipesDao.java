package com.example.android.bakingappudacity.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.bakingappudacity.RecipesModelJson.RecipesModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<RecipesModel> recipesModel);

    @Query("SELECT * from RecipesModel")
    LiveData<List<RecipesModel>> getAll();

    @Query("DELETE FROM RecipesModel")
    void DeleteAll();
}
