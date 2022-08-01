package com.cst2335.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.homePageBtn).setOnClickListener((click) ->{
            Intent goToHome = new Intent(MainActivity.this, HomePage.class);
            startActivity(goToHome);
        });
        findViewById(R.id.resultPageBtn).setOnClickListener((click) ->{
            Intent goToResult = new Intent(MainActivity.this, result_page.class);
            EditText et_area = findViewById(R.id.et_area);
            goToResult.putExtra("area", et_area.getText().toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.home_item:
                message = "You clicked on home";
                // this will stop the activity and start it again, instead of starting
                // a new activity over the existent one.
                this.finish();
                this.startActivity(getIntent());
                break;
            case R.id.cook_item:
                message = "You clicked on cook";
                Intent ii = new Intent (getApplicationContext(), result_page.class);
                startActivity(ii);
                break;
            case R.id.favourites_item:
                message = "You clicked on favourites";
                Intent iii = new Intent (getApplicationContext(), Favourites.class);
                startActivity(iii);
                break;
            case R.id.help_item:
                message = "You clicked on help";
                break;
        }
        if ( message != null ) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String message = null;
        switch (item.getItemId()){
            case R.id.drawerHome:
                Intent i1 = new Intent (getApplicationContext(), HomePage.class);
                startActivity(i1);
                break;
            case R.id.drawerResults:
                Intent i2 = new Intent (getApplicationContext(), result_page.class);
                startActivity(i2);
                break;
            case R.id.drawerFavourites:
                Intent i3 = new Intent (getApplicationContext(), Favourites.class);
                startActivity(i3);
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}