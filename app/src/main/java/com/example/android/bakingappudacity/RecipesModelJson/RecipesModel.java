
package com.example.android.bakingappudacity.RecipesModelJson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "RecipesModel")
public class RecipesModel implements Parcelable
{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    @Embedded
    private ArrayList<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    @Embedded
    private ArrayList<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private int servings;
    @SerializedName("image")
    @Expose
    private String image;
    public final static Parcelable.Creator<RecipesModel> CREATOR = new Creator<RecipesModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RecipesModel createFromParcel(Parcel in) {
            return new RecipesModel(in);
        }

        public RecipesModel[] newArray(int size) {
            return (new RecipesModel[size]);
        }

    }
    ;

    @Ignore
    protected RecipesModel(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readArrayList((com.example.android.bakingappudacity.RecipesModelJson.Ingredient.class.getClassLoader()));
        in.readArrayList((com.example.android.bakingappudacity.RecipesModelJson.Step.class.getClassLoader()));
        this.servings = ((int) in.readValue((int.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    @Ignore
    public RecipesModel() {
    }

    /**
     * 
     * @param ingredients
     * @param id
     * @param servings
     * @param name
     * @param image
     * @param steps
     */

    public RecipesModel(int id, String name, ArrayList<Ingredient> ingredients, @Nullable ArrayList<Step> steps, int servings, String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}
