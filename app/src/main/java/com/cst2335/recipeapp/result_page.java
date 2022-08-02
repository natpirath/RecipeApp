package com.cst2335.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cst2335.recipeapp.model.Meals;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cst2335.recipeapp.model.Meals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class result_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // fields to be initialized and used in the code
    MyListAdapter myAdapter;
    ListView resultListView;
    ArrayList<Meals> detailsList = new ArrayList<>(); //Detail object Array to store info of the list item
    TextView recipeName; //text view to set its text
    ImageView thumbnail;
    String mealName, mealThumb, idMeal, area;
    ProgressBar progressBar;
    Context context = this;
    JsonFetcher fetch;
    int resultCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        // For ToolBar
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

        // get the Intent extras that was passed in from mainActivity
        Intent thisIntent = getIntent();
        String areaPassed = thisIntent.getStringExtra("area");
        // if the activity accessed from toolbar and not from the search page
        if(areaPassed == null) {
            area = "Canadian"; // default area to get it meals
        }
        else {
            area = thisIntent.getStringExtra("area"); // whatever is passed form the search page.
        }
        resultListView = findViewById(R.id.list_view_SR);
        resultListView.setAdapter( myAdapter = new MyListAdapter());

        String api = "https://www.themealdb.com/api/json/v1/1/filter.php?a="+area;

        /* progress bar is visible on screen */
        progressBar = findViewById(R.id.SR_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        /* to fetch the data from the api */
        fetch = new JsonFetcher();
        fetch.execute(api);


        // FAB when clicked will show AlertDialog with "help" instructions on how to use the layout
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( clickFab -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle(R.string.help)
                    .setMessage(R.string.search_result_help)
                    .setNegativeButton("Close", (click, arg) -> {})
                    .create().show();
        }); //end fab onClick

        // this is to react on clicking on any list item, it should take us to the container activity that loads RecipePage fragment
        resultListView.setOnItemClickListener( (list, view, position, id) -> {
            // a toast message with the name of the meal clicked
            Toast.makeText(this,
                    "Recipe for "+myAdapter.getItem(position).getMealName(),
                        Toast.LENGTH_LONG).show();
            // name of the meal clicked in a bundle to be passed to a fragment
            Bundle fragmentData =new Bundle();
            fragmentData.putString("idMeal", myAdapter.getItem(position).getIdMeal());

            Intent recipeFrag = new Intent(this, FragmentContainer.class);
            recipeFrag.putExtras(fragmentData);
            startActivity(recipeFrag);

        });

    } // end onCreate method

    /**
     * Adapter for the listView to show the items in the list
     */
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
            View newView = inflater.inflate(R.layout.recipes_list_card, parent, false);

            //set what the text should be in this layout's text views:
            recipeName = newView.findViewById(R.id.tv_meal_name);
            recipeName.setText( getItem(position).getMealName() );


            // set the background image of the cardView "the meal image"
            String url = getItem(position).getMealImage();
            thumbnail = newView.findViewById(R.id.meal_img);
            // using Glide library we can load an image form a url into an imageView
            // placeholder is what shows while the image is loading
            Glide.with(newView)
                    .load(url)
                    .into(thumbnail);

            //return it to be put in the table
            return newView;
        }
    }// end myListAdapter

    private class JsonFetcher extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... args) {

            try {
                // URL object of the api we will use
                URL url = new URL(args[0]);
                // open a connection with the url
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                // wait for data to be retrieved
                InputStream response = urlConnection.getInputStream();
                // in case we don't get any response
                if(response == null){
                    return "Data not fetched";
                }

                // reading the Json data:-
                // build the json string from the input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder strBuilder = new StringBuilder();

                String line = null;
                // read the lines in the string read from the response
                while( (line = reader.readLine()) != null ) {
                    strBuilder.append(line).append("\n");
                }
                // return the Json string to be worked on in the onPostExecute() method
                return strBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }// end doInBackground

        /**
         * this method runs on the main UI thread and controls it.
         * it reads data form the Json string provided by the doInBackground, and passes them to
         * a new Meals object to be viewed on the listView.
         * @param s the returned string from doInBackground() method
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // after the doInBackground is done we make the progressbar invisible
            progressBar.setVisibility(View.INVISIBLE);

            // in case the data was not fetched
            if( s != null && s.equalsIgnoreCase("Data not fetched") ) {
                // show alert dialog with an error message
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle(R.string.help)
                        .setMessage(R.string.not_fetched)
                        .setNegativeButton("Close", (click, arg) -> {})
                        .create().show();
                Log.e("json", "Data is not fetched");
            }
            // or if it was fetched, do the Json parsing
            else {
                try {
                    // convert the string we built to JSON
                    JSONObject jObject = new JSONObject(s);


                    // fetch the Json array with the key "meals"
                    JSONArray mealsArray = jObject.getJSONArray("meals");


                    for (int i=0; i < mealsArray.length(); i++) {
                        JSONObject oneMeal = mealsArray.getJSONObject(i);

                        mealName = oneMeal.getString("strMeal");
                        mealThumb = oneMeal.getString("strMealThumb");
                        idMeal = oneMeal.getString("idMeal");

                        detailsList.add(new Meals(idMeal, mealName, mealThumb));
                        myAdapter.notifyDataSetChanged();

                    }

                    // the count of items that will be set on the listView
                    resultCount = mealsArray.length();
                    Toast.makeText(context, "we found "+resultCount+" results", Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }// end JsonFetcher

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
                // this will stop the activity and start it again, instead of starting
                // a new activity over the existent one.
                this.finish();
                this.startActivity(getIntent());
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
        return true;
    }


}// end class result_page




