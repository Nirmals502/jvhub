package com.example.deepsingh.jbhub;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView Img_back;
    String Str_lat,Str_lng;
    Double Lat,Lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Img_back = (ImageView) findViewById(R.id.imageView7);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            Str_lat = extras.getString("value_lat");
            Str_lng= extras.getString("value_lng");
            // and get whatever type user account id is
        }
        Lat= Double.parseDouble(Str_lat);
        Lng= Double.parseDouble(Str_lng);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //float zoomLevel = 16;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Lat, Lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Property Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom -14));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 15.0f));
    }

}
