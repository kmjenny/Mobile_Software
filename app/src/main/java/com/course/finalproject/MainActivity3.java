package com.course.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CalendarView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase sqlDB = null;

    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

//    CalendarView calendar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
//
//        calendarView = findViewById(R.id.calendarView);
//        recyclerView = findViewById(R.id.calendarRecyclerview);
//        userDatabaseHelper = new UserDatabaseHelper(this);
//        sqlDB = userDatabaseHelper.getReadableDatabase();
//
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
//                String sel_date = year + "-" + month + "-" + dayOfMonth;
//                Cursor cursor = sqlDB.rawQuery("SELECT * FROM meallist WHERE date = ?;", new String[]{sel_date});
//                ArrayList<String> name = new ArrayList<>();
//                ArrayList<String> date = new ArrayList<>();
//                ArrayList<String> time = new ArrayList<>();
//
//                while (cursor.moveToNext()){
//                    date.add(cursor.getString(0));
//                    time.add(cursor.getString(1));
//                    name.add(cursor.getString(2));
//                }
//
//                String[] array = new String[date.size()];
//                String[] array2 = new String[time.size()];
//                String[] array3 = new String[name.size()];
//
//                int size=0;
//                for(String temp : date){
//                    array[size++] = temp;
//                }
//
//                size=0;
//                for(String temp : time){
//                    array2[size++] = temp;
//                }
//                size=0;
//                for(String temp : name){
//                    array3[size++] = temp;
//                }
//
//                cursor.close();
//                sqlDB.close();
//
//                adapter = new MealAdapter(array,array2,array3,array4);
//                recyclerView.setAdapter(adapter);
//            }
//        });
//    }
}