package com.cst2335.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RecipePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
    }

    public void onCustomToggleClick(View view) {
        Toast.makeText(this, "Toggle",Toast.LENGTH_SHORT).show();
    }
}