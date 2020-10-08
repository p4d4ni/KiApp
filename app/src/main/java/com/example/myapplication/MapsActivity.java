package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private boolean locationPermissionGranted = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    //chur as default location if not granted access.
    private final LatLng defaultLocation = new LatLng(47.045576, 8.330917);

    //all used for mylocation button.
    Location location;
    private double currentLatitude = 0;
    private double currentLongitude = 0;

    //activity with text input.
    private Intent textInput;

    private boolean isMapReady = false;

    ArrayList<Marker> listOfPoints = new ArrayList<>();
    ArrayList<MarkerOptions> listOfP = new ArrayList<>();

    /*private int[] farbe = {R.drawable.rotmarker, R.drawable.blaumarker, R.drawable.gelbmarker, R.drawable.gruenmarker
            , R.drawable.turqmarker, R.drawable.pinkmarker};*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getLocationPermission();

        textInput = new Intent(this,  MapsActivity2.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        mMap = googleMap;
        isMapReady = true;

        loadData();

        if (locationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(MapsActivity.this);
        }
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {
                textInput.putExtra("location" , point);
                startActivityForResult(textInput,1);
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                listOfPoints.remove(marker);
                MarkerOptions p = new MarkerOptions().position(marker.getPosition()).title(marker.getTitle());
                listOfP.remove(p);
                marker.remove();
                saveData();
                return;
            }

            @Override
            public void onMarkerDrag(Marker marker) {

                return;
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                listOfPoints.remove(marker);
                MarkerOptions p = new MarkerOptions().position(marker.getPosition()).title(marker.getTitle());
                listOfP.remove(p);
                marker.remove();
                saveData();
                return;
            }
        });
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(defaultLocation, 7);
        mMap.moveCamera(cu);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        loadMarkers(listOfPoints);
    }

    //What happens as soon as startActivityForResult returns.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                MarkerOptions point1 = data.getParcelableExtra("marker");
                listOfP.add(point1);
                listOfPoints.add(mMap.addMarker(point1));
                saveData();
            }

        }
    }

    //what happens as soon as you click on my location button
    @Override
    public boolean onMyLocationButtonClick() {
        location = mMap.getMyLocation();
        currentLatitude = getLatitude();
        currentLongitude = getLongitude();

        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

        return false;
    }


    protected void onPause() {
        super.onPause();
        saveData();
    }
    protected void onResume(){
        super.onResume();
        loadData();
        loadMarkers(listOfPoints);
    }
    private void loadMarkers(List<Marker> listOfPoints) {
        int i=listOfPoints.size();
        while(i>0){
            i--;
            mMap.addMarker(new MarkerOptions().position(listOfPoints.get(i).getPosition()).title(listOfPoints.get(i).getTitle()).draggable(true));
            /*mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(Lat, Lon), 16));
        */
        }
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }




    public double getLatitude() {
        if (location != null) {
            currentLatitude = location.getLatitude();
        }
        // return latitude
        return currentLatitude;
    }

    public double getLongitude() {
        if (location != null) {
            currentLongitude = location.getLongitude();
        }
        // return longitude
        return currentLongitude;
    }


    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("places",  listOfP);
    }
    private void restore(Bundle outState){
        if (outState != null) {
            listOfP =(ArrayList<MarkerOptions>)outState.getSerializable("places");
        }
        int x = listOfP.size();
        for(int i = 0; i < x;i++){
            listOfPoints.add(mMap.addMarker(listOfP.get(i)));
        }
    }
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        restore(outState);
    }

    private void loadData(){
        try {
            FileInputStream input = openFileInput("latlngpoints.txt");
            DataInputStream din = new DataInputStream(input);
            int sz = din.readInt(); // Read line count
            for (int i = 0; i < sz; i++) {
                String str = din.readUTF();
                Log.v("read", str);
                String[] stringArray = str.split(",", 3);
                double latitude = Double.parseDouble(stringArray[0]);
                double longitude = Double.parseDouble(stringArray[1]);
                String mark = stringArray[2];
                //BitmapDescriptor farbe = BitmapDescriptorFactory.fromFile(stringArray[3]);
                //if(farbe == null){ farbe = BitmapDescriptorFactory.fromResource(R.drawable.blaumarker);}
                if(isMapReady) {
                    listOfP.add(new MarkerOptions().position(new LatLng(latitude,longitude)).title(mark).draggable(true));
                    listOfPoints.add(mMap.addMarker(listOfP.get(i)));
                }
            }
            din.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void saveData(){
        try {
            // Modes: MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITABLE
            FileOutputStream output = openFileOutput("latlngpoints.txt",
                    Context.MODE_PRIVATE);
            DataOutputStream dout = new DataOutputStream(output);
            dout.writeInt(listOfPoints.size()); // Save line count
            for (MarkerOptions point : listOfP) {
                dout.writeUTF(point.getPosition().latitude + "," + point.getPosition().longitude + "," + point.getTitle());
                Log.v("write", point.getPosition().latitude + "," + point.getPosition().longitude + "," + point.getTitle());
            }
            dout.flush(); // Flush stream ...
            dout.close(); // ... and close.
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

}
