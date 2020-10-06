package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import java.util.Random;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;

import java.util.Random;

public class GradActivity extends AppCompatActivity {
    //neuer Randomgenerator
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;

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
    //spin animation kopiert von https://medium.com/@ssaurel/develop-a-roulette-game-for-android-316e349f91a
    public void spin(View wheel) {
        // we calculate random angle for rotation of our wheel
        degree = RANDOM.nextInt(360) + 720;
        // rotation effect on the center of the wheel
        RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3600);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        // we start the animation
        wheel.startAnimation(rotateAnim);
    }

}
