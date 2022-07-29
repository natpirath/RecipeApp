package com.cst2335.recipeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cst2335.recipeapp.model.Meals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class result_page extends AppCompatActivity {

    // fields to be initialized and used in the code
    MyListAdapter myAdapter;
    ListView resultListView;
    Button addBtn;
    ArrayList<Meals> detailsList = new ArrayList<>(); //Detail object Array to store info of the list item
    TextView recipeName, category; //text views to set their text
    ImageView thumbnail;
    String mealName, mealThumb, idMeal, strCategory;
    ProgressBar progressBar;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        resultListView = findViewById(R.id.list_view_SR);
        resultListView.setAdapter( myAdapter = new MyListAdapter());


        /* progress bar is visible on screen */
        progressBar = findViewById(R.id.SR_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener( click -> {



            JsonFetcher fetch = new JsonFetcher();
            fetch.execute();

            //here we need to store the data form the API, for now I use fixed text for testing
           // mealName = "Shakshuka";
            //mealThumb = "https://www.themealdb.com/images/media/meals/g373701551450225.jpg";
            //idMeal = "52963";
            strCategory = "Vegetarian";




        }); // end addBtn onClick

        // FAB when clicked will show AlertDialog with "help" instructions on how to use the layout
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( clickFab -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle(R.string.help)
                    .setMessage(R.string.search_result_help)
                    .setNegativeButton("Close", (click, arg) -> {})
                    .create().show();
        }); //end fab onClick

    }

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
        public Object getItem(int position) { return detailsList.get(position); }

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
            recipeName.setText( detailsList.get(position).getMealName() );


            // set the background image of the cardView "the meal image"
            String url = detailsList.get(position).getMealThumb();
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
        protected String doInBackground(String... strings) {

            try {
                // URL object of the api we will use
                URL url = new URL("https://www.themealdb.com/api/json/v1/1/filter.php?a=Canadian");
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
                    strBuilder.append(line + "\n");
                }
                // return the Json string to be worked on in the onPostExecute() method
                return strBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }// end doInBackground

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
                    // this list is to store the Json meals objects that are inside the mealsArray
                    ArrayList<JSONObject> mealsobjects = new ArrayList<>();

                    JSONObject oneMeal = mealsArray.getJSONObject(0);

                    mealName = oneMeal.getString("strMeal");
                    mealThumb = oneMeal.getString("strMealThumb");
                    idMeal = oneMeal.getString("idMeal");

                    detailsList.add(new Meals(idMeal, mealName, mealThumb));
                    myAdapter.notifyDataSetChanged();

                    /*for (int i=0; i < mealsArray.length(); i++) {
                        JSONObject

                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }// end JsonFetcher


}// end class result_page




