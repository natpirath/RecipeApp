package com.cst2335.recipeapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sp;
    private static final String SP_NAME = "myPref";
    EditText et_area;
    ImageView imageView;
    Toolbar myToolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ArrayList<Integer> randomImages = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // FAB when clicked will show AlertDialog with "help" instructions on how to use the layout
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( clickFab -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle(R.string.help)
                    .setMessage(R.string.search_help)
                    .setNegativeButton("Close", (click, arg) -> {})
                    .create().show();
        }); //end fab onClick

        // storing images in a list to show them randomly as a header image in the activity
        randomImages.add(R.drawable.p1);
        randomImages.add(R.drawable.p2);
        randomImages.add(R.drawable.p3);
        randomImages.add(R.drawable.p4);
        randomImages.add(R.drawable.p5);
        randomImages.add(R.drawable.p6);
        randomImages.add(R.drawable.cooking_image);



        et_area = findViewById(R.id.et_area);
        imageView = findViewById(R.id.ImgRandom);

        // getting the shared preferences and setting it in the search box
        sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        et_area.setText(sp.getString("area", ""));

        // setting a random image form the images array in the header image view
        Random random = new Random();
        int index = random.nextInt(randomImages.size());
        imageView.setImageResource( ( randomImages.get(index) ) );

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        //For NavigationDrawer:
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        findViewById(R.id.resultPageBtn).setOnClickListener((click) ->{

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("area", et_area.getText().toString()).apply();

            Intent goToResult = new Intent(MainActivity.this, result_page.class);

            goToResult.putExtra("area", et_area.getText().toString());
            startActivity(goToResult);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        switch (item.getItemId()){
            case R.id.drawerHome:
                Intent i1 = new Intent (getApplicationContext(), MainActivity.class);
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
        //Log.e("navDraw", "item clicked: "+item.getItemId());
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}