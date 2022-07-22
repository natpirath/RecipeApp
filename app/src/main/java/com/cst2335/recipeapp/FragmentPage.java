package com.cst2335.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cst2335.recipeapp.ui.main.FragmentPageFragment;

public class FragmentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_page);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FragmentPageFragment.newInstance())
                    .commitNow();
        }
    }
}