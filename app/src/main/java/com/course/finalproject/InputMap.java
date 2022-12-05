package com.course.finalproject;

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

import java.util.List;

public class InputMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView searchBox;
    private TextView locationText;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchBox = findViewById(R.id.Search);
        locationText = findViewById(R.id.locationText);
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String searchText = searchBox.getText().toString();
                intent.putExtra("place", searchText);

                Geocoder geocoder = new Geocoder(getBaseContext());
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocationName(searchText, 3);
                    if (addresses != null && !addresses.equals(" ")) {
                        search(addresses);
                    }
                } catch(Exception e) {

                }
            }
        });

//        intent.putExtra("place", "아무값");

        intent = new Intent(InputMap.this, MainActivity2.class);
        Button inputButton = findViewById(R.id.input_button);
        inputButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    protected void search(List<Address> addresses) {
        Address address = addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        String addressText = String.format(
                "%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address
                        .getAddressLine(0) : " ", address.getFeatureName());

        locationText.setVisibility(View.VISIBLE);
        locationText.setText("Latitude" + address.getLatitude() + "Longitude" + address.getLongitude() + "\n" + addressText);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(addressText);

        mMap.clear();
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        oneMarker();
    }

    public void oneMarker() {
        // 서울 여의도에 대한 위치 설정
        LatLng seoul = new LatLng(37.5582876, 127.00016710000001);

        // 구글맵에 표시할 마커에 대한 옵션 설정
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(seoul)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다")
                .snippet("여기는 동국대입니다")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .alpha(0.5f); // 알파는 좌표의 투명도

        // 마커를 생성한다
        mMap.addMarker(markerOptions); // .showInfoWindow() 를 쓰면 처음부터 마커에 상세정보가 뜬다

        // 정보창 클릭리스너
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        // 마커 클릭 리스너
        mMap.setOnMarkerClickListener(markerClickListener);

        // 카메라를 여의도 위치로 옮겨준다
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(InputMap.this, "눌렀습니다!!", Toast.LENGTH_LONG);
                return false;
            }
        });
    }

    // 정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            Toast.makeText(InputMap.this, "정보창 클릭 Marker ID : "
                    + markerId, Toast.LENGTH_SHORT).show();
        }
    };

    // 마커 클릭 리스너
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();
            // 선택한 타겟의 위치
            LatLng location = marker.getPosition();
            Toast.makeText(InputMap.this, "마커 클릭 Marker ID : "
                            + markerId + "(" + location.latitude + " " + location.longitude + ")",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    };
}