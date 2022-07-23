package com.cst2335.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.homePageBtn).setOnClickListener((click) ->{
            Intent goToHome = new Intent(MainActivity.this, HomePage.class);
            startActivity(goToHome);
        });
        findViewById(R.id.resultPageBtn).setOnClickListener((click) ->{
            Intent goToResult = new Intent(MainActivity.this, result_page.class);
            startActivity(goToResult);
        });
        findViewById(R.id.fragmentPageBtn).setOnClickListener((click) ->{
            Intent goToFragment = new Intent(MainActivity.this, RecipePage.class);
            startActivity(goToFragment);
        });
        findViewById(R.id.favouritesPageBtn).setOnClickListener((click) ->{
            Intent goToFavourites = new Intent(MainActivity.this, Favourites.class);
            startActivity(goToFavourites);
        });

    }
}