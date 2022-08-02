package com.cst2335.recipeapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

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