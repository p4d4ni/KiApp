package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MapsActivity2 extends AppCompatActivity {

    private EditText text;
    private ImageButton back;
    private int farbe = R.drawable.rotmarker;
    private Button Rot,Gelb,Blau,Turquois,Pink,Grün;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        final LatLng latlng = (LatLng)getIntent().getParcelableExtra("location");

        back = (ImageButton)findViewById(R.id.BackButton);
        text = (EditText) findViewById(R.id.editText);

        Rot =(Button)findViewById(R.id.buttonRot);
        Gelb = (Button)findViewById(R.id.buttonGelb);
        Blau =(Button)findViewById(R.id.buttonBlau);
        Turquois =(Button)findViewById(R.id.buttonTur);
        Pink =(Button)findViewById(R.id.buttonPink);
        Grün =(Button)findViewById(R.id.buttonGrün);

        Rot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                farbe = R.drawable.rotmarker;
                submit(latlng);
            }
        });
        Gelb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                farbe = R.drawable.gelbmarker;
                submit(latlng);
            }
        });
        Blau.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                farbe = R.drawable.blaumarker;
                submit(latlng);
            }
        });
        Turquois.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                farbe = R.drawable.turqmarker;
                submit(latlng);
            }
        });
        Pink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                farbe = R.drawable.pinkmarker; ;
                submit(latlng);
            }
        });
        Grün.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                farbe = R.drawable.gruenmarker;
                submit(latlng);
            }
        });


        back.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });




    }
    public void submit(LatLng latlng){
        //MarkerOptions marker = new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(farbe));
        MarkerOptions marker = new MarkerOptions().position(latlng).draggable(true);
        if (text.getText() != null) {
            marker.title(text.getText().toString());
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("marker", marker);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}