package com.lista.listdeactiviti;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lista.listdeactiviti.Adapter.CustomAdapter;
import com.lista.listdeactiviti.DataBase.DataBaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn_add_item_activity;
    RecyclerView recyclerView;

    DataBaseHelper myDB;
    ArrayList<String> activity_id, activity_title;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add_item_activity=findViewById(R.id.btn_add_item_activity);
        recyclerView=findViewById(R.id.recyclerview);
        btn_add_item_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        myDB=new DataBaseHelper(MainActivity.this);
        activity_id=new ArrayList<>();
        activity_title=new ArrayList<>();

        storeDataInArray();

        customAdapter=new CustomAdapter(MainActivity.this,MainActivity.this, activity_id,activity_title);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    void storeDataInArray(){
        Cursor cursor=myDB.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "Nu sunt activități!", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                activity_id.add(cursor.getString(0));
                activity_title.add(cursor.getString(1));
            }
        }

    }
}