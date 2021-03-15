package com.lista.listdeactiviti.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lista.listdeactiviti.ItemList;
import com.lista.listdeactiviti.R;
import com.lista.listdeactiviti.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

   private Context context;
   private ArrayList activity_id, activity_title;

   Activity activity;
   Animation animation;


    public CustomAdapter(Activity activity, Context context, ArrayList activity_id, ArrayList activity_title){

        this.activity=activity;
        this.context=context;
        this.activity_id=activity_id;
        this.activity_title=activity_title;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view= layoutInflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.activity_id_txt.setText(String.valueOf(activity_id.get(position)));
        holder.activity_title_txt.setText(String.valueOf(activity_title.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ItemList.class);
                intent.putExtra("id",String.valueOf(activity_id.get(position)));
                intent.putExtra("title",String.valueOf(activity_title.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return activity_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView activity_id_txt, activity_title_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            activity_id_txt=itemView.findViewById(R.id.id_activity_txt);
            activity_title_txt=itemView.findViewById(R.id.title_activity_txt);
            mainLayout=itemView.findViewById(R.id.mainLayout);

            animation= AnimationUtils.loadAnimation(context,R.anim.trasntlate_anim);
            mainLayout.setAnimation(animation);
        }
    }
}
