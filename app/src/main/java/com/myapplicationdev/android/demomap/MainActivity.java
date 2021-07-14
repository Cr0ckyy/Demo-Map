package com.myapplicationdev.android.demomap;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        // Interface for interacting with Fragment objects inside of an Activity
        FragmentManager fm = getSupportFragmentManager();

        // TODO: A Map component in an app. This fragment is the simplest way to place a map in an application.
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        /*TODO: Assertions should be used to check for something that should never happen,
           whereas exceptions (try-catch statement) should be used to check for something that could happen.*/
        assert mapFragment != null;

       /* TODO: mapFragment.getMapAsync() returns a reference to a Google Map object.
            When the object is entirely loaded, it will be ready.
            Because the time required for this object is dependent on the Internet connection,
            it is tracked individually.*/
        mapFragment.getMapAsync(googleMap -> {
            map = googleMap;

             /* TODO:Classes that are immutable and represent a pair of
                 latitude and longitude coordinates saved as degrees.*/
            LatLng poi_CausewayPoint = new LatLng(1.436065, 103.786263);
            LatLng JurongEast = new LatLng(1.3329, 103.7436);

            /* TODO: Icons that are placed at specific points on the map's surface. */
            Marker cp = map.addMarker(new MarkerOptions()
                    .position(poi_CausewayPoint)
                    .title("Causeway Point")
                    .snippet("Shopping after class")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            Marker je = map.addMarker(new MarkerOptions()
                    .position(JurongEast)
                    .title("Jurong East")
                    .snippet("C347 Android Programming II")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

            /* TODO: classes provide methods for constructing CameraUpdate objects that alter the camera on a map*/
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_CausewayPoint, 15));
            map.moveCamera(CameraUpdateFactory.newLatLng(JurongEast));

            /* TODO: Settings for the user interface of a GoogleMap.*/
            UiSettings ui = map.getUiSettings();

            // setting Compass & ZoomControls
            ui.setCompassEnabled(true);
            ui.setZoomControlsEnabled(true);

            // an object that determines whether you have been granted access to a fine location.
            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

            /* TODO: If permission is granted, the ability to set the map location will be enabled.*/
            if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true); // Enables the my-location layer.

                //If this permission is not granted, the class activity will request that permissions be granted to this application.
            } else {
                Log.e("Gmap-Permission", "Gps access has not been granted");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        });

        btn1.setOnClickListener(v -> {
            assert map != null;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);  // Sets the map's type to Basic map.
        });

        btn2.setOnClickListener(v -> {
            assert map != null;
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN); // The map's type is changed to Topographic data map.
        });

        btn3.setOnClickListener(v -> {
            assert map != null;
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE); // The map's type is changed to Satellite imagery map.
        });
    }
}