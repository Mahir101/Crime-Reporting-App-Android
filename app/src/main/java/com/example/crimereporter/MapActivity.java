//package com.example.crimereporter;
//
//import android.graphics.BitmapFactory;
//import android.location.Location;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.mapbox.geojson.Feature;
//import com.mapbox.geojson.FeatureCollection;
//import com.mapbox.geojson.Point;
//import com.mapbox.mapboxsdk.Mapbox;
//import com.mapbox.mapboxsdk.annotations.Marker;
//import com.mapbox.mapboxsdk.annotations.MarkerOptions;
//import com.mapbox.mapboxsdk.geometry.LatLng;
//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
//import com.mapbox.mapboxsdk.maps.Style;
//import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
//import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
//import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
//
////import com.mapbox.mapboxandroiddemo.R;
//
///**
// * Display {@link SymbolLayer} icons on the map.
// */
//public class MapActivity extends AppCompatActivity implements
//        OnMapReadyCallback {
//
//    private static final String SOURCE_ID = "SOURCE_ID";
//    private static final String ICON_ID = "ICON_ID";
//    private static final String LAYER_ID = "LAYER_ID";
//
//    private static final String TAG = "tagging" ;
//    //private MapView mapView;
//    private MapboxMap map;
//    //private PermissionsManager permissionsManager;
//   // private LocationEngine locationEngine;
//   // private LocationLayerPlugin locationLayerPlugin;
//    private Location originLocation;
//    private Point originPosition;
//    private Point destinationPositon;
//    private Marker destinationMarker;
//    String lat="";
//    String lng="";
//    private MapView mapView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Mapbox access token is configured here. This needs to be called either in your application
//        // object or in the same activity which contains the mapview.
//        Mapbox.getInstance(this, getString(R.string.access_token));
//
//        // This contains the MapView in XML and needs to be called after the access token is configured.
//        setContentView(R.layout.activity_map);
//
//        mapView = findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);
//
//
//
//
//
//    }
//
//    @Override
//    public void onMapClick(@NonNull LatLng point){
//        if(destinationMarker!=null){
//            map.removeMarker(destinationMarker);
//        }
//        destinationMarker = map.addMarker(new MarkerOptions().position(point));
//        destinationPositon = Point.fromLngLat(point.getLongitude(),point.getLatitude());
//        originPosition = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());
//
//        lat =  String.valueOf(point.getLatitude());
//        lng = String.valueOf(point.getLongitude());
//
//    }
//
//    @Override
//    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
//
//        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
//        symbolLayerIconFeatureList.add(Feature.fromGeometry(
//                Point.fromLngLat(-57.225365, -33.213144)));
//        symbolLayerIconFeatureList.add(Feature.fromGeometry(
//                Point.fromLngLat(-54.14164, -33.981818)));
//        symbolLayerIconFeatureList.add(Feature.fromGeometry(
//                Point.fromLngLat(-56.990533, -30.583266)));
//
//        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
//
//                // Add the SymbolLayer icon image to the map style
//                .withImage(ICON_ID, BitmapFactory.decodeResource(
//                        MapActivity.this.getResources(), R.drawable.red_marker))
//
//                // Adding a GeoJson source for the SymbolLayer icons.
//                .withSource(new GeoJsonSource(SOURCE_ID,
//                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
//
//                // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
//                // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
//                // the coordinate point. This is offset is not always needed and is dependent on the image
//                // that you use for the SymbolLayer icon.
//                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
//                        .withProperties(PropertyFactory.iconImage(ICON_ID),
//                                iconAllowOverlap(true),
//                                iconOffset(new Float[] {0f, -9f}))
//                ), new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//
//                // Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.
//
//
//            }
//        });
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//}












package com.example.crimereporter;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibWFoaXIxMDEiLCJhIjoiY2swOGd0NHp4MGkzazNtcGF oMDEwY24zNCJ9.meBvyMfZAJHCcgTG3bNLxQ");
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        startButton = findViewById(R.id.btnDone);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MapActivity.this, PostActivity.class);

                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    startActivity(intent);
                    finish();

            }
        });

    }
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.addOnMapClickListener(this);
      //  enableLocation();
    }
//    private void enableLocation(){
//        if(PermissionsManager.areLocationPermissionsGranted(this)){
//            initializeLocationEngine();
//            initializeLocationLayer();
//        }
//        else{
//            permissionsManager = new PermissionsManager(this);
//            permissionsManager.requestLocationPermissions(this);
//        }
//    }
//    @SuppressWarnings("MissingPermission")
//    private void initializeLocationEngine(){
//        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
//        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
//        locationEngine.activate();
//        Location lastLocation= locationEngine.getLastLocation();
//        if(lastLocation!=null){
//            originLocation = lastLocation;
//            setCameraPosition(lastLocation);
//        }
//        else {
//            locationEngine.addLocationEngineListener(this);
//        }
//    }
//    private void initializeLocationLayer(){
//        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
//        locationLayerPlugin.setLocationLayerEnabled(true);
//        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
//        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
//
//    }
//    private void setCameraPosition(Location location){
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0));
//    }
    @Override
    public void onMapClick(@NonNull LatLng point){
        if(destinationMarker!=null){
            map.removeMarker(destinationMarker);
        }
        try {
            destinationMarker= map.addMarker(new MarkerOptions().position(point));
            destinationPositon = Point.fromLngLat(point.getLongitude(), point.getLatitude());
            //originPosition = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());

            lat = String.valueOf(destinationPositon.longitude());
            lng = String.valueOf(destinationPositon.latitude());

            Log.d(TAG,lat);
            Log.d(TAG,lng);

            //startButton.setEnabled(true);
            //startButton.setBackgroundResource(R.color.mapboxBlue);
        }
        catch(Exception e){
            toastmessage("kichu vhul ache");
        }
    }

//    @Override
//    public void onConnected() {
//        locationEngine.requestLocationUpdates();
//    }

/*    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            originLocation = location;
            setCameraPosition(location);
        }
    }*/
//
//    @Override
//    public void onExplanationNeeded(List<String> permissionsToExplain) {
//
//    }

/*     @Override
   public void onPermissionResult(boolean granted) {
        if(granted){
            //enableLocation();
        }
    }*/
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }


    @SuppressWarnings("MissingPermission")
    //@Override
    public void onStart() {
        super.onStart();
  /*      if(locationEngine!=null){
            locationEngine.requestLocationUpdates();
        }

        if(locationLayerPlugin != null){
            locationLayerPlugin.onStart();
        }*/
        mapView.onStart();
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
        Toast.makeText(MapActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
