package com.cst2335.recipeapp;


import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cst2335.recipeapp.model.Meals;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class RecipePage extends Fragment {

    final String TAG = "recipeAct";
    String idMeal, mealName, mealThumb;
    private View view;

    // Required empty public constructor
    public RecipePage() { }


    /* onCreate() is not used in a fragment the course slides says, but maybe it can be used I don't know */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public void onCustomToggleClick(View view) {
        //Toast.makeText(this, "Toggle", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "star clicked");
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View recipePage =inflater.inflate(R.layout.activity_recipe_page, container, false);

        ProgressBar progressBar = recipePage.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle passedData = getArguments();
        idMeal = passedData.getString("idMeal");

        String api = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="+idMeal;
        JsonFetcher fetcher = new JsonFetcher();
        fetcher.execute(api);

            TextView tv_test = recipePage.findViewById(R.id.textView2);
            tv_test.setText("ONLY TESTING: Meal ID from API is  " + passedData.getString("idMeal"));

        return recipePage;
    }




    private class JsonFetcher extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... args) {

            try {
                Log.e(TAG, "in doInBackground");
                // URL object of the api we will use
                URL url = new URL(args[0]);
                Log.e(TAG, "url: "+url);
                // open a connection with the url
                HttpURLConnection Connection = (HttpURLConnection) url.openConnection();
                // wait for data to be retrieved
                InputStream stream = Connection.getInputStream();
                Log.e(TAG, "stream: "+stream);
                // in case we don't get any stream
                if(stream == null){
                    return "Data not fetched";
                }

                // reading the Json data:-
                // build the json string from the input stream
                BufferedReader myReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();

                String line1 = null;
                // read the lines in the string read from the stream
                while( (line1 = myReader.readLine()) != null ) {
                    stringBuilder.append(line1).append("\n");
                }
                // return the Json string to be worked on in the onPostExecute() method
                return stringBuilder.toString();

            } catch (Exception e) {
                Log.e(TAG, "exception in doInBackground");
                e.printStackTrace();
            }
            return null;
        }// end doInBackground

        /**
         * this method runs on the main UI thread and controls it.
         * it reads data form the Json string provided by the doInBackground, and passes them to
         * a new Meals object to be viewed on the listView.
         * @param s1 the returned string from doInBackground() method
         */
        @Override
        protected void onPostExecute(String s1) {
            super.onPostExecute(s1);
            Log.e(TAG, "in onPostExecute");
            Log.e(TAG, "result from fetching: "+s1);
            // after the doInBackground is done we make the progressbar invisible
           // progressBar.setVisibility(View.INVISIBLE);

            // in case the data was not fetched
            if( s1 != null && s1.equalsIgnoreCase("Data not fetched") ) {
                // show alert dialog with an error message
                /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle(R.string.help)
                        .setMessage(R.string.not_fetched)
                        .setNegativeButton("Close", (click, arg) -> {})
                        .create().show();*/
                Log.e(TAG, "Data is not fetched");
            }
            else if(s1 == null){
                Log.e(TAG, " s1 is null");
            }
            // or if it was fetched, do the Json parsing
            else {
                try {
                    // convert the string we built to JSON
                    JSONObject jsonObject = new JSONObject(s1);
