package com.lista.listdeactiviti.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lista.listdeactiviti.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolderItem> {

    Activity activity;
    private Context context;
    private ArrayList<String> item;

    public ItemAdapter(Activity activity, Context context, ArrayList item) {
        this.activity = activity;
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.my_items,parent,false);
        return new ItemAdapter.MyViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolderItem holder, int position) {

        holder.title_item_row.setText(String.valueOf(item.get(position)));

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolderItem extends RecyclerView.ViewHolder {

        TextView title_item_row;

        public MyViewHolderItem(@NonNull View itemView) {
            super(itemView);

            title_item_row = itemView.findViewById(R.id.title_item_row);
        }
    }
}
