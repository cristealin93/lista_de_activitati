package com.lista.listdeactiviti;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lista.listdeactiviti.Adapter.ItemAdapter;
import com.lista.listdeactiviti.DataBase.DataBaseHelper;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    String id, title;

    FloatingActionButton btn_add_item;
    RecyclerView recyclerView_item;

    DataBaseHelper myDB;
    ArrayList<String> activity_item;
    ImageView img_nodata_item;
    TextView txt_nodata_item;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        img_nodata_item=findViewById(R.id.no_data_item);
        txt_nodata_item=findViewById(R.id.txt_nodata_item);
        btn_add_item=findViewById(R.id.btn_add_item);
        recyclerView_item=findViewById(R.id.recyclerview_item);
        myDB=new DataBaseHelper(ItemList.this);
        activity_item=new ArrayList<>();

        getAndSetIntentData();
        storeDataInArrayItem();

        itemAdapter=new ItemAdapter(ItemList.this,ItemList.this,activity_item);
        recyclerView_item.setAdapter(itemAdapter);
        recyclerView_item.setLayoutManager(new LinearLayoutManager(ItemList.this));

        ActionBar ab=getSupportActionBar();
        assert ab != null;
        if(title==null || title.isEmpty()) {
            ab.setTitle("Lista noua");
        }else{
            ab.setTitle(title);
        }

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ItemList.this,AddItem.class);
                intent.putExtra("id_fk",id);
                intent.putExtra("title_list",title);
                startActivityForResult(intent,1);
                finish();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")){
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");

        }else {
            Toast.makeText(this, "Nu sunt date despre activitati.", Toast.LENGTH_SHORT).show();
        }

    }

    void storeDataInArrayItem(){
        int val=Integer.parseInt(id);
        Cursor cursor=myDB.readDataFromItemTable(val);
        if(cursor.getCount()==0){
            img_nodata_item.setVisibility(View.VISIBLE);
            txt_nodata_item.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                activity_item.add(cursor.getString(1));
            }
            img_nodata_item.setVisibility(View.GONE);
            txt_nodata_item.setVisibility(View.GONE);
        }

    }

}