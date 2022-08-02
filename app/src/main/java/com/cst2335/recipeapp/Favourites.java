package com.cst2335.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cst2335.recipeapp.model.Meals;
import com.cst2335.recipeapp.model.MyOpenHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Favourites extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//---
    MyOpenHelper myOpener;
    SQLiteDatabase theDatabase;

    //MyListAdapter myAdapter;
   // Button btn;
    ListView listView;
    MyListAdapter myAdapter;
    //ListView resultListView;
    ArrayList<Meals> detailsList = new ArrayList<>(); //Detail object Array to store info of the list item
    TextView recipeName; //text view to set its text
    ImageView thumbnail;
    String mealName, mealThumb, idMeal, area;
    ProgressBar progressBar;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        //initialize it in onCreate
        myOpener = new MyOpenHelper( this );
        //open the database:
        theDatabase = myOpener.getWritableDatabase();

        Cursor results = theDatabase.rawQuery( "Select * from " + MyOpenHelper.TABLE_NAME + ";", null );//no arguments to the query


        listView = findViewById(R.id.listView);
        listView.setAdapter(myAdapter = new MyListAdapter());

        //Convert column names to indices:
        int idIndex = results.getColumnIndex( MyOpenHelper.COL_ID );
        int  mealNameIndex = results.getColumnIndex( MyOpenHelper.COL_MEAL_NAME);
        int mealImageIndex = results.getColumnIndex( MyOpenHelper.COL_IMAGE);

        //cursor is pointing to row -1
        while( results.moveToNext() ) //returns false if no more data
        { //pointing to row 2
            String id = results.getString(idIndex);
            String mealName = results.getString( mealNameIndex );
            String mealImage = results.getString(mealImageIndex);

            detailsList.add(new Meals(id, mealName, mealImage));
        }
        myAdapter.notifyDataSetChanged();




        // Get reference of widgets from XML layout
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

        listView.setOnItemClickListener( (list, view, position, id) -> {
            // a toast message with the name of the meal clicked
            Toast.makeText(this,
                    "Recipe for "+myAdapter.getItem(position).getMealName(),
                    Toast.LENGTH_SHORT).show();

            // name of the meal clicked in a bundle to be passed to a fragment
            Bundle fragmentData =new Bundle();
            fragmentData.putString("idMeal", myAdapter.getItem(position).getIdMeal());

            Intent recipeFrag = new Intent(this, FragmentContainer.class);
            recipeFrag.putExtras(fragmentData);
            startActivity(recipeFrag);
        });

        /*Button btn = findViewById(R.id.favBtn);
        btn.setOnClickListener(click ->{

            theDatabase.delete(MyOpenHelper.TABLE_NAME, "_id=?",
                    new String[]{idMeal});
        });*/

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
                Intent i = new Intent (getApplicationContext(), MainActivity.class);
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

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }



    private class MyListAdapter extends BaseAdapter {

        /**
         * when called will return the total number of objects in the list.
         * uses the array list which contains the Detail Objects i.e. the number of view cards on screen.
         * @return the total number of Detail Objects, size of the arrayList.
         */
        public int getCount() { return detailsList.size();}

        /**
         * This function to be called in the getView function, used to get the
         * object at the position passed needed to be displayed. Uses the ArrayList.
         * @param position index of the object to be displayed.
         * @return the object that will be displayed.
         */
        public Meals getItem(int position) { return detailsList.get(position); }

        /**
         * this function used to get the database ID of the object in the database.
         * @param position index of the item in the details ArrayList
         * @return database ID of the Detail object
         */
        public long getItemId(int position) {
            // for now it will only return the position on the list because we don't have database yet.
            return (long) position;
        }

        /**
         * used to list the items in the listView by inflating a layout and also edit any view in this layout
         * it inflates the recipes_list_card then gets the recipe name and category and sets them
         * in the corresponding text views.
         * @return the inflated view to be displayed in the listView
         */
        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            View newView = inflater.inflate(R.layout.favourites_list_card, parent, false);

            //set what the text should be in this layout's text views:
            recipeName = newView.findViewById(R.id.tv_meal_name_fav);
            recipeName.setText( getItem(position).getMealName() );


            // set the background image of the cardView "the meal image"
            String url = getItem(position).getMealImage();
            thumbnail = newView.findViewById(R.id.meal_img_fav);
            // using Glide library we can load an image form a url into an imageView
            // placeholder is what shows while the image is loading
            Glide.with(newView)
                    .load(url)
                    .into(thumbnail);

            //return it to be put in the table
            return newView;
        }
    }// end myListAdapter

}