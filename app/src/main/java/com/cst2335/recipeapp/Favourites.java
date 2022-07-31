package com.cst2335.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Favourites extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Get reference of widgets from XML layout
        final ListView listView = (ListView) findViewById(R.id.listView);
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



        // Initializing a new String Array
        String[] fruits = new String[] {
                "Poutine - Canadian!",
                "Butter Chicken - Indian!"
        };

        // Create a List from String Array elements
        final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, fruits_list);

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);




        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String whatWasClicked = fruits_list.get(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Favourites.this);
                alertDialogBuilder.setTitle("Make a choice")

                        //What is the message:
                        .setMessage("Do you want to delete this?")

                        //what the Yes button does:
                        .setPositiveButton("Yes", (click, arg) -> {
                            fruits_list.remove(position);

                            //elements.remove(elements.size() - 1);
                            //myAdapter.notifyItemRemoved();
                            arrayAdapter.notifyDataSetChanged();

                            /*Snackbar.make(null, "You removed " + position, Snackbar.LENGTH_SHORT)
                                    .setAction("Undo", (click2)-> {
                                fruits_list.add(position, whatWasClicked);
                                arrayAdapter.notifyDataSetChanged();
                            }).show();*/

                        })
                        //What the No button does:
                        .setNegativeButton("No", (click, arg) -> {

                        })

                        //An optional third button:
                        .setNeutralButton("Maybe", (click, arg) -> {
                        }).show();
                return true;
            }
        });

        listView.setOnItemClickListener((list, view, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("item", fruits_list.get(position));

            Toast.makeText(Favourites.this, getResources().getString(Integer.parseInt("R.string.favourites_toast")), Toast.LENGTH_SHORT).show();



            //Intent detailedActivity = new Intent(this, EmptyActivity.class);
            //detailedActivity.putExtras(dataToPass);
            //startActivity(detailedActivity, dataToPass);
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
                Intent i = new Intent (getApplicationContext(), HomePage.class);
                startActivity(i);
                break;
            case R.id.cook_item:
                message = "You clicked on cook";
                Intent ii = new Intent (getApplicationContext(), result_page.class);
                startActivity(ii);
                break;
            case R.id.favourites_item:
                message = "You clicked on favourites";
                // this will stop the activity and start it again, instead of starting
                // a new activity over the existent one.
                this.finish();
                this.startActivity(getIntent());
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