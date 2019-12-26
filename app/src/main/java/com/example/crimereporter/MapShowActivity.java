package com.example.crimereporter;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import java.util.List;

public class MapShowActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener, LocationEngineListener {
    private static final String TAG = "tagging" ;
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Point originPosition;
    private Point destinationPositon;
    private Marker destinationMarker;
    private Button startButton;
    String lat=null;
    String lng=null;

    FirebaseDatabase fd;
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "sk.eyJ1IjoibWFoaXIxMDEiLCJhIjoiY2s0bHcyMnQ3MDl4MjNlbzU5cXdyaTh2biJ9.hYnR4VtvLxz-_rNzBwoGgA");
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        startButton = findViewById(R.id.btnDone);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mref = fd.getInstance().getReference().child("Loc");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapShowActivity.this, UIActivity.class);
                startActivity(intent);
                finish();
                //enableLocation();

            }
        });

    }
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.addOnMapClickListener(this);

        enableLocation();


        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //res.add(new Entry(,cnt)) ;

                    Locations dp = ds.getValue(Locations.class);
                    dp.getLat();
                    dp.getLng();

                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(dp.getLat(), dp.getLng()))
                            .title("Crime-Spot"));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        map.addMarker(new MarkerOptions()
                .position(new LatLng(24.8939, 91.8683))
                .title("Crime Tower"));

    }
    private void enableLocation(){
        if(PermissionsManager.areLocationPermissionsGranted(this)){
            initializeLocationEngine();
            initializeLocationLayer();
        }
        else{
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }
    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine(){
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
        Location lastLocation= locationEngine.getLastLocation();
        if(lastLocation!=null){
            originLocation = lastLocation;
            setCameraPosition(lastLocation);
        }
        else {
            locationEngine.addLocationEngineListener(this);
        }
    }
    private void initializeLocationLayer(){
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);

    }
    private void setCameraPosition(Location location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 11.0));
    }
    @Override
    public void onMapClick(@NonNull LatLng point){
        if(destinationMarker!=null){
            map.removeMarker(destinationMarker);
        }
        try {
           // destinationMarker= map.addMarker(new MarkerOptions().position(point));
            //destinationPositon = Point.fromLngLat(point.getLongitude(), point.getLatitude());
            //originPosition = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());

            //lat = String.valueOf(destinationPositon.longitude());
            //lng = String.valueOf(destinationPositon.latitude());

            //Log.d(TAG,lat);
            //Log.d(TAG,lng);

            startButton.setEnabled(true);
            startButton.setBackgroundResource(R.color.mapboxBlue);
        }
        catch(Exception e){
            toastmessage("kichu vhul ache");
        }
    }

    @Override
    @SuppressWarnings("MissingPernussion")
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            originLocation = location;
            setCameraPosition(location);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

     @Override
   public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @SuppressWarnings("MissingPermission")
    //@Override
    public void onStart() {
        super.onStart();
        if(locationEngine!=null){
            locationEngine.requestLocationUpdates();
        }

        if(locationLayerPlugin != null){
            locationLayerPlugin.onStart();
        }
        mapView.onStart();

       // destinationMarker= map.addMarker(new MarkerOptions().position(point));


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(locationEngine!=null){
            locationEngine.removeLocationUpdates();
        }

        if(locationLayerPlugin != null){
            locationLayerPlugin.onStop();
        }

        mapView.onStop();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationEngine!=null){
            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public  void toastmessage(String message){
        Toast.makeText(MapShowActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
