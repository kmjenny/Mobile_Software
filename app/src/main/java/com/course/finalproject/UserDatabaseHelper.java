package com.course.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public UserDatabaseHelper(Context context) {
        super(context, "groupDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE meallist (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "date String, time String, foodname String, amount int, calorie double, place String, comment String, image String);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meallist");
        onCreate(db);

    }
}
