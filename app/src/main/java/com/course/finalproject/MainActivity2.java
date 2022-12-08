package com.course.finalproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    ImageView imageView;

    private TextView name;
    private TextView ammount;
    private TextView review;
    private TextView places;
    private TextView date;
    private TextView time;

    private final String NAME = "";
    private String name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        name = findViewById(R.id.Name);
        ammount = findViewById(R.id.Ammount);
        review = findViewById(R.id.Review);

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

        Button next = (Button)findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
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
                time.setText(String.valueOf(i+":"+i1));
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

    public void addMeal(View view) {
        ContentValues addValues = new ContentValues();
        addValues.put(MyContentProvider.NAME,name.getText().toString());
        addValues.put(MyContentProvider.AMMOUNT,ammount.getText().toString());
        addValues.put(MyContentProvider.REVIEW,review.getText().toString());
        addValues.put(MyContentProvider.PLACE,places.getText().toString());
        addValues.put(MyContentProvider.DATE,date.getText().toString());
        addValues.put(MyContentProvider.TIME,time.getText().toString());

        getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);

    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState){
//        outState.putString(NAME,name.getText().toString());
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
//        super.onRestoreInstanceState(savedInstanceState);
//        name2 = savedInstanceState.getString(NAME);
//        name.setText(name2);
//    }

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
                        Log.e(TAG, "uri : " + uri);
//                        imageview.setImageURI(uri);
                        Glide.with(MainActivity2.this)
                                .load(uri)
                                .into(imageView);
                    }
                }
            });
}

