package com.course.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class InputMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    Button inputButton;
    private String address = null;
    private Geocoder geocoder;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_map);

        geocoder = new Geocoder(this, Locale.KOREA);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inputButton = findViewById(R.id.input_button);
        inputButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("place", address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    MarkerOptions markerOptions = new MarkerOptions();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng start = new LatLng(37.55827, 126.998425);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        mMap.clear();

        latitude = point.latitude;
        longitude = point.longitude;

        geocoderthread thread = new geocoderthread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        markerOptions.title("Place");
        markerOptions.snippet(address);
        markerOptions.position(new LatLng(latitude, longitude));

        mMap.addMarker(markerOptions);
    }

    class geocoderthread extends Thread {
        public void run() {
            List<Address> addlist = null;
            try {
                addlist = geocoder.getFromLocation(latitude, longitude, 10);
            } catch (IOException e) {
            }

            if (addlist != null) {
                if (addlist.size() == 0)
                {
                    address = "No Address";
                }
                else
                {
                    address = addlist.get(0).getAddressLine(0);
                }
            }
        }
    }
}