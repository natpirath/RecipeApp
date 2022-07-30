package com.cst2335.recipeapp;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RecipePage extends Fragment {

    // Required empty public constructor
    public RecipePage() { }

    /* onCreate() is not used in a fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
    }

    public void onCustomToggleClick(View view) {
        Toast.makeText(this, "Toggle",Toast.LENGTH_SHORT).show();
    }
    */

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View recipePage =inflater.inflate(R.layout.activity_recipe_page, container, false);

        Bundle passedData = getArguments();

        TextView tv_test = recipePage.findViewById(R.id.textView2);
        tv_test.setText("ONLY TESTING: Meal ID from API is  "+passedData.getString("idMeal"));

        return recipePage;
    }
}