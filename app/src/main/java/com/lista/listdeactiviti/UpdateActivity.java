package com.lista.listdeactiviti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lista.listdeactiviti.DataBase.DataBaseHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input;
    Button update_button;

    String id, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input=findViewById(R.id.edt_add_activity2);
        update_button=findViewById(R.id.btn_update);

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper myDB=new DataBaseHelper(UpdateActivity.this);
                myDB.updateData(id,title_input.getText().toString());
            }
        });



    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")){
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");

            title_input.setText(title);
        }else {
            Toast.makeText(this, "Nu sunt date despre activitati.", Toast.LENGTH_SHORT).show();
        }

    }
}