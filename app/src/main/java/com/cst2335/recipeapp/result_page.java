package com.cst2335.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class result_page extends AppCompatActivity {

    private ArrayList<String> elements = new ArrayList<>(  );
    MyListAdapter myAdapter;

    ListView resultListView;
    Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        resultListView = findViewById(R.id.list_view_SR);

        resultListView.setAdapter( myAdapter = new MyListAdapter());

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener( click -> {

            elements.add( "Another Row");
            myAdapter.notifyDataSetChanged();
        });

    }

    /**
     * Adapter for the listView to show the items in the list
     */
    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return elements.size();}

        public Object getItem(int position) { return elements.get(position).toString(); }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            View newView = inflater.inflate(R.layout.test_row_layout, parent, false);

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText( getItem(position).toString() );

            //Button b =  newView.findViewById(R.id.textGoesHere);
            //b.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;
        }
    }


/*    public static class RecipeDetail {
        String name;
        ArrayList<String> categories;
        long _id;

        public RecipeDetail(String name, ArrayList<String> categories, long _id) {
            this.name = name;
            this.categories = categories;
            this._id = _id;
        }

        public String getName() {return name;}
        public ArrayList<String> getCategories() {return categories;}
        public long get_id() {return _id;}


    }// end class RecipeDetail*/
}// end class result_page




