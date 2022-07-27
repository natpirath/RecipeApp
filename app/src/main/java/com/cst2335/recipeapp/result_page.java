package com.cst2335.recipeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cst2335.recipeapp.model.Meals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        resultListView = findViewById(R.id.list_view_SR);
        resultListView.setAdapter( myAdapter = new MyListAdapter());

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener( click -> {
            //here we need to store the data form the API, for now I use fixed text for testing
            mealName = "Shakshuka";
            mealThumb = "https://www.themealdb.com/images/media/meals/g373701551450225.jpg";
            idMeal = "52963";
            strCategory = "Vegetarian";

            detailsList.add(new Meals(idMeal, mealName, mealThumb));
            myAdapter.notifyDataSetChanged();
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

            category = newView.findViewById(R.id.tv_categories);
            category.setText( "Vegetarian" );

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
    }


}// end class result_page




