package com.lista.listdeactiviti.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.lista.listdeactiviti.DataBase.DataBaseHelper;
import com.lista.listdeactiviti.ItemList;
import com.lista.listdeactiviti.MainActivity;
import com.lista.listdeactiviti.R;
import com.lista.listdeactiviti.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList activity_id, activity_title,fkList;
    private View bau;

    MainActivity mainActivity;
    Activity activity;
    Animation animation;
    DataBaseHelper myDB;
    int count=0, countNumberOfItem=0;

    public CustomAdapter(Activity activity, Context context, ArrayList activity_id, ArrayList activity_title, ArrayList fkList) {

        this.activity = activity;
        this.context = context;
        this.activity_id = activity_id;
        this.activity_title = activity_title;
        this.fkList = fkList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.activity_id_txt.setText(String.valueOf(activity_id.get(position)));
        holder.activity_title_txt.setText(String.valueOf(activity_title.get(position)));
        for(int i=0;i<fkList.size();i++){
            if(holder.activity_id_txt.getText().equals(String.valueOf(fkList.get(i)))){
                countNumberOfItem++;
            }
        }
        if(countNumberOfItem>0){
            holder.progressBar.setMax(10);
            holder.progressBar.setProgress(countNumberOfItem);

        }else{
            holder.progressBar.setMax(0);
            holder.progressBar.setProgress(0);
        }
        holder.txt_quantity_item.setText(holder.activity_id_txt.getText()+" / "+ String.valueOf(fkList.get(0)));



        holder.img_dot_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.list_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.edit:

                                holder.edt_Rename.setVisibility(View.VISIBLE);
                                holder.edt_Rename.setText(holder.activity_title_txt.getText());
                                holder.activity_title_txt.setVisibility(View.GONE);
                                holder.edt_Rename.setOnKeyListener(new View.OnKeyListener() {
                                    @Override
                                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                                            holder.activity_title_txt.setText(holder.edt_Rename.getText().toString());
                                            myDB = new DataBaseHelper(context);
                                            myDB.RenameList((String) holder.activity_id_txt.getText(), (String) holder.activity_title_txt.getText());
                                            holder.edt_Rename.setVisibility(View.GONE);
                                            holder.activity_title_txt.setVisibility(View.VISIBLE);

                                            return true;
                                        }

                                        return false;
                                    }
                                });

                                break;
                            case R.id.delete:
                                myDB = new DataBaseHelper(context);
                                myDB.deleteData(String.valueOf(activity_id.get(position)));
                                deleteItem(position, (String) holder.activity_id_txt.getText(), (String) holder.activity_title_txt.getText(), v);
                                break;
                        }

                        return false;
                    }
                });
            }
        });




        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemList.class);
                intent.putExtra("id", String.valueOf(activity_id.get(position)));
                intent.putExtra("title", String.valueOf(activity_title.get(position)));
                activity.startActivityForResult(intent, 1);
            }

        });

    }

    private void deleteItem(int position, String id, String title, View view) {
        activity_id.remove(position);
        activity_title.remove(position);
        notifyDataSetChanged();
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position,activity_id.size());

        Snackbar.make(view, "Lista " + title + " eliminata?", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<activity_id.size();i++){
                            if(activity_id.get(i)==id){
                                count++;
                            }
                        }
                        if(count>0){
                            Toast.makeText(context, "Aceasta lista exista deja!", Toast.LENGTH_SHORT).show();
                        }else{
                            myDB.undoList(id, title);
                            activity_id.add(position, id);
                            activity_title.add(position, title);
                            notifyDataSetChanged();
                        }
                        count =0;
                    }
                }).show();

    }

    @Override
    public int getItemCount() {
        return activity_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView activity_id_txt, activity_title_txt, txt_quantity_item;
        LinearLayout mainLayout;
        ImageView img_dot_menu;
        EditText edt_Rename;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            activity_id_txt = itemView.findViewById(R.id.id_activity_txt);
            txt_quantity_item = itemView.findViewById(R.id.txt_quantity_item);
            edt_Rename = itemView.findViewById(R.id.edt_Rename);
            activity_title_txt = itemView.findViewById(R.id.title_activity_txt);
            img_dot_menu = itemView.findViewById(R.id.img_dot_menu);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            progressBar = itemView.findViewById(R.id.progressBar);

            animation = AnimationUtils.loadAnimation(context, R.anim.trasntlate_anim);
            mainLayout.setAnimation(animation);
        }
    }

}
