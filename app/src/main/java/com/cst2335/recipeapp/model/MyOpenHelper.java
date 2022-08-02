package com.cst2335.recipeapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "MealDatabase";
    public static final int version = 1;
    public static final String TABLE_NAME = "Meal";
    public static final String COL_ID = "_id";
    public static final String COL_MEAL_NAME = "MealName";
    public static final String COL_IMAGE = "Image";

    public MyOpenHelper(Context context) {

        super(context, DATABASE, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table MyData ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Message TEXT, SendOrReceive INTEGER);
        // String result = String.format(" %s %s %s", "FirstString" , "10", "10.0" );

        //                                      //TABLE_NAME               take care of id numbers
        db.execSQL( String.format( "Create table %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s  TEXT);"
                , TABLE_NAME, COL_ID, COL_MEAL_NAME, COL_IMAGE ) );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "Drop table if exists " + TABLE_NAME ); //deletes the current data
        //create a new table:

        this.onCreate(db); //calls function on line 26
    }
}
