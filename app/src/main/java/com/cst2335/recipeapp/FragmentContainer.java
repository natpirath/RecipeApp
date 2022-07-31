package com.cst2335.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FragmentContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        Bundle passedData = getIntent().getExtras();

        RecipePage recipeFragment = new RecipePage( );
        recipeFragment.setArguments(passedData);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragFrameLayout, recipeFragment)
                . commit();
    }
}