package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class GradActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gluecksrad);

        final ImageView myView = (ImageView)findViewById(R.id.glueck);
        final Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        rotation.setRepeatCount(Animation.INFINITE);
        Button button = (Button)findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation(myView,rotation);
            }
        });

    }
    protected void animation(ImageView view, Animation rotation) {
        Random generator = new Random();
        for (int i = 0; i < generator.nextInt(); i++) {
            view.startAnimation(rotation);
        }
    }

}
