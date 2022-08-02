package com.cst2335.recipeapp.model;

import java.util.ArrayList;

public class Meals {

    String idMeal;
    String mealName;
    String category;
    String area;
    String instructions;
    String mealImage;
    String favStatus;
    ArrayList<String> ingredients = new ArrayList<>();
    ArrayList<String> measurements = new ArrayList<>();


    public Meals(){};
    /**
     * constructor for fetching Json by filtering area
     * @param idMeal
     * @param mealName
     * @param mealImage
     */
    public Meals(String idMeal, String mealName, String mealImage, String favStatus) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.mealImage = mealImage;
        this.favStatus = favStatus;
    }
    public Meals(String idMeal, String mealName, String mealImage) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.mealImage = mealImage;
    }

    /**
     * constructor for the full details of a meal
     * @param idMeal
     * @param mealName
     * @param category
     * @param area
     * @param instructions
     * @param mealImage
     * @param ingredients
     * @param measurements
     */
    public Meals(String idMeal, String mealName, String category, String area, String instructions, String mealImage, ArrayList<String> ingredients, ArrayList<String> measurements) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.mealImage = mealImage;
        this.ingredients = ingredients;
        this.measurements = measurements;
    }

    /* getters */
    public String getIdMeal() {
        return idMeal;
    }
    public String getMealName() {
        return mealName;
    }
    public String getCategory() {
        return category;
    }
    public String getArea() {
        return area;
    }
    public String getInstructions() {
        return instructions;
    }
    public String getMealImage() {
        return mealImage;
    }
    public String getFavStatus() {
        return favStatus;
    }
    public ArrayList<String> getIngredients() {
        return ingredients;
    }
    public ArrayList<String> getMeasurements() {
        return measurements;
    }

    /* setters */
    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void setMeasurements(ArrayList<String> measurements) {
        this.measurements = measurements;
    }
}
