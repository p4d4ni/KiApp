package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMap = findViewById(R.id.buttonMaps);
        Button buttonGrad = findViewById(R.id.buttonGluecksrad);

        //initializes both activities, maps and glücksrad, further activities must be declared here
        final Intent maps = new Intent(this,  MapsActivity.class);
        final Intent grad = new Intent(this,  GradActivity.class);

        buttonMap.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View arg0) {
                startActivity(maps);
            }
        });
        buttonGrad.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View arg0) {
                startActivity(grad);
            }
        });
    }
}