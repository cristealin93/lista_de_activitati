package com.lista.listdeactiviti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lista.listdeactiviti.DataBase.DataBaseHelper;

public class AddItem extends AppCompatActivity {

    Button btn_adauga, btn_inapi_la_lista;
    EditText edt_item;
    String fk_id,list_title;
    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        btn_adauga=findViewById(R.id.btn_adauga);
        btn_inapi_la_lista=findViewById(R.id.btn_inapi_la_lista);
        edt_item=findViewById(R.id.edt_item);
        myDB=new DataBaseHelper(AddItem.this);

        getAndSetIntentData();

        btn_adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper myDB = new DataBaseHelper(AddItem.this);
                if(edt_item.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddItem.this, "Tastati un produs ", Toast.LENGTH_SHORT).show();
                }else{
                    myDB.addNewItem(edt_item.getText().toString().trim(),fk_id);
                }

            }
        });

        btn_inapi_la_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddItem.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id_fk") && getIntent().hasExtra("title_list")){
            fk_id=getIntent().getStringExtra("id_fk");
            list_title=getIntent().getStringExtra("title_list");

        }else {
            Toast.makeText(this, "Nu sunt date despre activitati.", Toast.LENGTH_SHORT).show();
        }

    }
}