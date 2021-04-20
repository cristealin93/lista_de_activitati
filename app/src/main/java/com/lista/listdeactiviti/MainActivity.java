package com.lista.listdeactiviti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
    ImageView ing_nodata;
    TextView txt_nodata;
    ArrayList<String> fk_array;
    int count=0;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_add_item_activity=findViewById(R.id.btn_add_item_activity);
        recyclerView=findViewById(R.id.recyclerview);
        ing_nodata=findViewById(R.id.no_data);
        txt_nodata=findViewById(R.id.txt_nodata);

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
        fk_array=new ArrayList<String>();

        storeDataInArray();
        storeFkColumnForProgressBar();
        customAdapter=new CustomAdapter(MainActivity.this,MainActivity.this, activity_id,activity_title,fk_array);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void storeFkColumnForProgressBar() {
        Cursor cursorFk=myDB.readFkColumn();
        if(cursorFk.getCount()==0){
            Toast.makeText(this, "Creaza o lista", Toast.LENGTH_SHORT).show();
        }else {
            while (cursorFk.moveToNext()){
                fk_array.add(cursorFk.getString(0));
                System.out.println(fk_array.get(count++));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

   public void storeDataInArray(){
        Cursor cursor=myDB.readAllData();
        if(cursor.getCount()==0){
            ing_nodata.setVisibility(View.VISIBLE);
            txt_nodata.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                activity_id.add(cursor.getString(0));
                activity_title.add(cursor.getString(1));
            }
            ing_nodata.setVisibility(View.GONE);
            txt_nodata.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_menu){
           confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sterge toate activitatile?");
        builder.setMessage("Esti sigur ca vrei sa stergi toate activitatile?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataBaseHelper myDB=new DataBaseHelper(MainActivity.this);
                myDB.deleteAllData();
                Toast.makeText(MainActivity.this, "Toate activitatile au fost sterse.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}