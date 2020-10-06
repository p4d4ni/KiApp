package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Gluecksrad extends AppCompatActivity {

    public ImageView  myImage ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gluecksrad);
        final Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
        ((ImageButton)findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View arg0) {
                myImage.startAnimation(myRotation);
            }
        });
    }

}
