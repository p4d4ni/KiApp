package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class GradActivity extends AppCompatActivity {
    Random generator = new Random();
    float degreeOld = 0;
    float degree = 0;
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
                spin(myView);
            }
        });


    }
    public void spin(View v){
        degreeOld = degree % 360;
        // we calculate random angle for rotation of our wheel
        degree = generator.nextInt(360) + 720;
        // rotation effect on the center of the wheel
        RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3600);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        v.startAnimation(rotateAnim);
    }

}
