package com.cst2335.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class result_page extends AppCompatActivity {

    // fields to be initialized and used in the code
    MyListAdapter myAdapter;
    ListView resultListView;
    Button addBtn;
    ArrayList<Detail> detailsList = new ArrayList<>(); //Detail object Array to store info of the list item
    TextView recipeName, category; //text views to set their text
    String mealName;
    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        resultListView = findViewById(R.id.list_view_SR);
        resultListView.setAdapter( myAdapter = new MyListAdapter());

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener( click -> {
            //here we need to store the meal name and the categories form the API, for now I use fixed text
            mealName = "Shakshuka";
            categories =  new ArrayList<>();
            categories.add("Breakfast");
            categories.add("Vegan");
            detailsList.add(new Detail(mealName, categories));
            myAdapter.notifyDataSetChanged();
        });

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
            category = newView.findViewById(R.id.tv_categories);
            recipeName.setText( detailsList.get(position).getMealName() );
            category.setText( String.join(", ", detailsList.get(position).getCategories()) );

            //return it to be put in the table
            return newView;
        }
    }

    /**
     * This class used for the details of the meal that will be displayed on the listView
     *
     */
    private static class Detail {
        String mealName;
        ArrayList<String> categories;
        long _id;

        public Detail(String mealNameIn, ArrayList<String> categoriesIn) {
            this.mealName = mealNameIn;
            this.categories = categoriesIn;
        }

        public String getMealName() {return mealName;}

        public ArrayList<String> getCategories() {return categories;}
    }// end Detail Class

}// end class result_page




