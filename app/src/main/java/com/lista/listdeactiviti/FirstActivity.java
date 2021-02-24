package com.lista.listdeactiviti;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FirstActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackgroundResource(0);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

    }
}