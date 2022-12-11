package com.course.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MainHolder> {
    private String[] name, date, time;
    private Integer[] id;

    MainHolder mainHolder;

    public MealAdapter(String[] name, String[] date, String[] time, Integer[] id){
        this.name = name;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public static class MainHolder extends  RecyclerView.ViewHolder{
        public TextView name, date, time;
        public MainHolder(View view){
            super(view);
            //뷰객체에 대한 참조
            this.name = view.findViewById(R.id.name);
            this.date = view.findViewById(R.id.date);
            this.time = view.findViewById(R.id.time);

        }
    }
    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list, parent, false);
        mainHolder = new MainHolder(holderView);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int i) {
        mainHolder.name.setText(this.name[i]);
        mainHolder.date.setText(this.date[i]);
        mainHolder.time.setText(this.time[i]);

        mainHolder.name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("id",id[i]);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return name.length;
    }
}
