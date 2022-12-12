package com.course.finalproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase sqlDB = null;

    TextView name;
    TextView amount;
    TextView date;
    TextView time;
    TextView review;
    TextView place;
    TextView calories;
    ImageView img;

    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        ImageButton b1 = (ImageButton) findViewById(R.id.imageButton);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Detail.this, MonthList.class);
                startActivity(intent);
            }
        });

        userDatabaseHelper = new UserDatabaseHelper(this);
        sqlDB = userDatabaseHelper.getReadableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM meallist WHERE _id = '"+id+"';", null);
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> amount = new ArrayList<>();
        ArrayList<String> review = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> place = new ArrayList<>();
        ArrayList<String> cal = new ArrayList<>();
        ArrayList<String> img = new ArrayList<>();

        while (cursor.moveToNext()){
            date.add(cursor.getString(1));
            time.add(cursor.getString(2));
            name.add(cursor.getString(3));
            amount.add(cursor.getString(4));
            cal.add(cursor.getString(5));
            place.add(cursor.getString(6));
            review.add(cursor.getString(7));
            img.add(cursor.getString(8));
        }

        this.name = (TextView) findViewById(R.id.name);
        this.amount = (TextView) findViewById(R.id.amount);
        this.date = (TextView) findViewById(R.id.date);
        this.time = (TextView) findViewById(R.id.time);
        this.review = (TextView) findViewById(R.id.review);
        this.place = (TextView) findViewById(R.id.place);
        calories = (TextView) findViewById(R.id.calories);
        this.img = (ImageView) findViewById(R.id.imageView);

        int size = 0;
        for(String temp : date){
            this.date.setText(temp);
        }
        size = 0;
        for(String temp : time){
            this.time.setText(temp);
        }
        size = 0;
        for(String temp : name){
            this.name.setText(temp);
        }
        size = 0;
        for(String temp : amount){
            this.amount.setText(temp);
        }
        size = 0;
        for(String temp : cal){
            this.calories.setText(temp+"kcal");
        }
        size = 0;
        for(String temp : place){
            temp = temp.substring(temp.indexOf(" ")+1);
            this.place.setText(temp);
        }
        size = 0;
        for(String temp : review){
            this.review.setText(temp);
        }
        size = 0;
        for(String temp : img){
//            try {
//                File files = new File(temp);
//                if(files.exists()==true){
//                    Uri uri = Uri.parse(temp);
//                    this.img.setImageURI(uri);
//                }
//            }catch(Exception e){
//                e.printStackTrace();
//            }
            try {
                Uri uri = Uri.parse(temp);
                InputStream in = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                this.img.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }

        cursor.close();
        sqlDB.close();
    }

}

