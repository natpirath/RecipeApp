package com.cst2335.recipeapp.model;

import java.util.ArrayList;

public class Meals {

    String idMeal;
    String mealName;
    String category;
    String area;
    String instructions;
    String mealThumb;
    ArrayList<String> ingredients = new ArrayList<>();
    ArrayList<String> measurements = new ArrayList<>();

    /**
     * constructor for fetching Json by filtering area
     * @param idMeal
     * @param mealName
     * @param mealThumb
     */
    public Meals(String idMeal, String mealName, String mealThumb) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.mealThumb = mealThumb;
    }

    /**
     * constructor for the full details of a meal
     * @param idMeal
     * @param mealName
     * @param category
     * @param area
     * @param instructions
     * @param mealThumb
     * @param ingredients
     * @param measurements
     */
    public Meals(String idMeal, String mealName, String category, String area, String instructions, String mealThumb, ArrayList<String> ingredients, ArrayList<String> measurements) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.mealThumb = mealThumb;
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
    public String getMealThumb() {
        return mealThumb;
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
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setMealThumb(String mealThumb) {
        this.mealThumb = mealThumb;
    }
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void setMeasurements(ArrayList<String> measurements) {
        this.measurements = measurements;
    }
}
