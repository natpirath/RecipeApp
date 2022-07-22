package com.cst2335.recipeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Favourites extends AppCompatActivity {

//---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Get reference of widgets from XML layout
        final ListView listView = (ListView) findViewById(R.id.listView);

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

}