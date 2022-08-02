package com.cst2335.recipeapp.model;

public class FavItem {

    private String idMeal;
    private String mealName;
    private int mealImage;

    public FavItem(){
    }

    public FavItem(String mealName, String idMeal, int mealImage){
        this.mealName = mealName;
        this.idMeal = idMeal;
        this.mealImage = mealImage;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getMealImage() {
        return mealImage;
    }

    public void setMealImage(int mealImage) {
        this.mealImage = mealImage;
    }
}
