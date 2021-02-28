package com.lista.listdeactiviti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lista.listdeactiviti.DataBase.DataBaseHelper;

public class AddActivity extends AppCompatActivity {

    EditText title_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input=findViewById(R.id.edt_add_activity);
        add_button=findViewById(R.id.btn_add);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper myDB=new DataBaseHelper(AddActivity.this);
                myDB.addNewActivity(title_input.getText().toString().trim());
            }
        });

    }
}