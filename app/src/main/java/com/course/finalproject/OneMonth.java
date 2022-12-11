package com.course.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OneMonth extends AppCompatActivity {
    UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase sqlDB = null;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    TextView total;

    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
    String month;
    String year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_month);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLayoutManager);
        month = monthFormat.format(currentTime);
        year = yearFormat.format(currentTime);

        ImageButton back = (ImageButton)findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(OneMonth.this, MonthList.class);
                startActivity(intent);
            }
        });

        String now_date = year + "-" + month + "%";
        userDatabaseHelper = new UserDatabaseHelper(this);
        sqlDB = userDatabaseHelper.getReadableDatabase();
        Double cal = 0.0;

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM meallist WHERE date LIKE '"+now_date+"';", null);
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<String> calories = new ArrayList<>();

        while (cursor.moveToNext()){
            id.add(cursor.getInt(0));
            date.add(cursor.getString(1));
            time.add(cursor.getString(2));
            name.add(cursor.getString(3));
            calories.add(cursor.getString(5));
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
        size=0;
        for(String temp : calories){
            Double i = Double.parseDouble(temp);
            cal = cal + i;
        }

        cursor.close();
        sqlDB.close();

        total = (TextView)findViewById(R.id.total);
        total.setText(cal.toString()+"kcal");

        MealAdapter adapter = new MealAdapter(array3,array,array2,array4);
        recyclerView.setAdapter(adapter);
    }
}