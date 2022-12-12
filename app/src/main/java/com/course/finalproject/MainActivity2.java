package com.course.finalproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    ImageView imageView;
    UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
    SQLiteDatabase sqlDB;

    EditText name;
    EditText amount;
    EditText review;
    TextView places;
    TextView date;
    TextView time;
    public static String na;
    public static int am;
    public static double calory;
    meal ml = new meal();

    public static Uri uri2;

    private void readData(String name){
        InputStream is = getResources().openRawResource(R.raw.mealdb);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line;
        try{
            reader.readLine();

            while ((line = reader.readLine()) != null){
                String[] tokens = line.split(",");

                if (tokens[0].equals(name)){
                    ml.setName(tokens[0]);
                    ml.setCar(Double.parseDouble(tokens[1]));
                }
            }

        } catch (IOException e) {
            Log.d("MyActivity", "Error reading data file on line");
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button next = (Button)findViewById(R.id.button_next);

        Button list = (Button)findViewById(R.id.button_list);
        list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity2.this, MonthList.class);
                startActivity(intent);
            }
        });

        places = (TextView)findViewById(R.id.Place);
        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, InputMap.class);
                startActivityForResult(intent,3000);
            }
        });

        date = (TextView) findViewById(R.id.Date);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.setText(year + "-" + (month+1) + "-" + day);
            }
        },mYear,mMonth,mDay);

        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(date.isClickable()){
                    datePickerDialog.show();
                }
            }
        });

        time = (TextView)findViewById(R.id.Time);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                time.setText(String.valueOf(i+" : "+i1));
            }
        }, mHour, mMinute, false);


        time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(time.isClickable()){
                    timePickerDialog.show();
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.main_image);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            launcher.launch(intent);
        });

        name = (EditText) findViewById(R.id.Name);
        amount = (EditText) findViewById(R.id.Amount);
        review = (EditText) findViewById(R.id.Review);

        next.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity2.this, MonthList.class);

            String name = this.name.getText().toString();
            String amount = this.amount.getText().toString();
            String date = this.date.getText().toString();
            String review = this.review.getText().toString();
            String place = this.places.getText().toString();
            String time = this.time.getText().toString();
            String image = this.uri2.toString();
//            String image = getRealPathFromURI(uri2);

            am = Integer.parseInt(amount);
            readData(name);
            this.calory=ml.getCar();

            sqlDB = userDatabaseHelper.getWritableDatabase();
            sqlDB.execSQL("INSERT INTO meallist VALUES (NULL,'" + date + "','" + time + "' , '" + name + "' , '" + am + "' , '" + calory*am + "' , '" + place + "', '" + review + "', '" + image + "');");
            sqlDB.close();
            startActivity(intent2);
        });
    }

    public String getRealPathFromURI(Uri contentUri){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,proj,null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 3000:
                    places.setText(data.getStringExtra("place"));
                    break;
            }
        }
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Log.e(TAG, "result : " + result);
                        Intent intent = result.getData();
                        Log.e(TAG, "intent : " + intent);
                        Uri uri = intent.getData();
                        uri2 = uri;
                        Log.e(TAG, "uri : " + uri);
//                        imageview.setImageURI(uri);
                        Glide.with(MainActivity2.this)
                                .load(uri)
                                .into(imageView);
                    }
                }
            });
}

