package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.Arrays;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private boolean locationPermissionGranted = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private MapView mapView;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.



    //chur as default location if not granted access.
    private final LatLng defaultLocation = new LatLng(47.045576, 8.330917);
    private static final int DEFAULT_ZOOM = 10;

    LatLng loc;
    Location location;
    private double currentLatitude = 0;
    private double currentLongitude = 0;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        getLocationPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
        if(locationPermissionGranted){
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(MapsActivity.this);
        }
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(defaultLocation, 7);
        mMap.moveCamera(cu);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Marker in schweiz"));




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

    public double getLatitude(){
        if(location != null){
            currentLatitude = location.getLatitude();
        }
        // return latitude
        return currentLatitude;
    }

    public double getLongitude(){
        if(location != null){
            currentLongitude = location.getLongitude();
        }
        // return longitude
        return currentLongitude;
    }

    //what happens as soon as you click on my location button
    @Override
    public boolean onMyLocationButtonClick() {
        location=mMap.getMyLocation();
        currentLatitude=getLatitude();
        currentLongitude=getLongitude();

        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        //mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in Current Location"));


        return false;
    }
}