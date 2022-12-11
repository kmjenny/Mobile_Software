package com.course.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.Month;
import java.util.ArrayList;

public class MonthList extends AppCompatActivity {
    UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase sqlDB = null;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLayoutManager);

        Button month = (Button) findViewById(R.id.button1);
        month.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MonthList.this, OneMonth.class);
                startActivity(intent);
            }
        });

        ImageButton add = (ImageButton) findViewById(R.id.imageButton);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MonthList.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        userDatabaseHelper = new UserDatabaseHelper(this);
        sqlDB = userDatabaseHelper.getReadableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM meallist;", null);
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<Integer> id = new ArrayList<>();

        while (cursor.moveToNext()){
            id.add(cursor.getInt(0));
            date.add(cursor.getString(1));
            time.add(cursor.getString(2));
            name.add(cursor.getString(3));
        }

        String[] array = new String[date.size()];
        String[] array2 = new String[time.size()];
        String[] array3 = new String[name.size()];
        Integer[] array4 = new Integer[id.size()];

        int size=0;
        for(String temp : date){
            array[size++] = temp;
        }

        size=0;
        for(String temp : time){
            array2[size++] = temp;
        }
        size=0;
        for(String temp : name){
            array3[size++] = temp;
        }
        size=0;
        for(Integer temp : id){
            array4[size++] = temp;
        }

        cursor.close();
        sqlDB.close();

        MealAdapter adapter = new MealAdapter(array3,array,array2,array4);
        recyclerView.setAdapter(adapter);
    }
}