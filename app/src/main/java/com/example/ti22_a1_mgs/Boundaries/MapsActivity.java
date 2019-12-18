package com.example.ti22_a1_mgs.Boundaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.example.ti22_a1_mgs.Database.RouteViewModel;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.GeoFencing;
import com.example.ti22_a1_mgs.R;
import com.example.ti22_a1_mgs.utils.CustomRoutingListener;
import com.example.ti22_a1_mgs.utils.LocationUtil;
import com.example.ti22_a1_mgs.utils.MapUtil;
import com.example.ti22_a1_mgs.utils.MarkerUtil;
import com.example.ti22_a1_mgs.utils.PopupUtil;

import com.example.ti22_a1_mgs.utils.RouteUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        DialogInterface.OnClickListener,
        CustomRoutingListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLoadedCallback,
        Observer<List<PointOfInterest>> {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private static int MAX_MARKER_VISIBLE = 3;

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location userLocation;
    private LocationRequest locationRequest; //strong reference

    private RouteViewModel viewModelThing;

    private boolean loadingFirstTime = true;
    private List<PointOfInterest> pointOfInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = findViewById(R.id.custom_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.name_map_activity);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        finish();
                        break;
                }
                return true;
            }

        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.viewModelThing = ViewModelProviders.of(this).get(RouteViewModel.class);

//        this.viewModelThing.deleteAllDatabaseContents();

//        this.viewModelThing.fillDatabaseFromData(
//                new BlindWallsBreda().getAllWalls(), this
//        );
    }

    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MapUtil.setMapStyling(this, map);

        initializeMapClients();

        this.viewModelThing.getAllPointsOfInterest().observe(this, this);
    }

    private void initializeMapClients() {
        boolean success = LocationUtil.checkLocationPermission(this);

        if (success) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();

            MapUtil.setMapSettings(map);

            map.setOnInfoWindowClickListener(this);
            map.setOnMapLoadedCallback(this);
        } else {
            finish();
        }
    }

    private void drawRoute(final CustomRoutingListener listener) {
        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
            @Override
            public void onChanged(List<Waypoint> waypoints) {
                Log.d(TAG, String.valueOf(waypoints.size()));

                //clear map
                MapUtil.clearMap(map);

                //create lists for data tracking
                List<LatLng> locations = new ArrayList<>();
                List<Waypoint> nonVisitedWaypoints = new ArrayList<>();
                List<PointOfInterest> nonVisitedPointOfInterests = new ArrayList<>();

                //clone all non visited waypoints
                for (int i = 0; i < MAX_MARKER_VISIBLE; i++) {
                    if (!waypoints.get(i).isVisited()) {
                        nonVisitedWaypoints.add(waypoints.get(i));
                        if (pointOfInterests != null)
                            nonVisitedPointOfInterests.add(pointOfInterests.get(i));
                    }
                }

                if (nonVisitedWaypoints.size() == 0)
                    return;


                //get first waypoint
                Waypoint firstWaypoint = nonVisitedWaypoints.get(0);

                //create the rest of the polylines
                while (nonVisitedWaypoints.size() != 0) {

                    //get LatLng
                    LatLng newPos = new LatLng(nonVisitedWaypoints.get(0).getLat(), nonVisitedWaypoints.get(0).getLon());
                    locations.add(newPos);

                    //get drawable for marker
                    Drawable resource = null;
                    String addres = UUID.randomUUID().toString().substring(0, 10);
                    if (nonVisitedPointOfInterests.size() != 0) {
                        try {
                           String imgUrl = nonVisitedPointOfInterests.get(0).getImgUrls().get(0).replace("static/","");
                           addres = nonVisitedPointOfInterests.get(0).getAddres();

                            //get image
                            InputStream ims = getAssets().open("BWImages/"+ imgUrl);
                            // load image as Drawable
                            resource = Drawable.createFromStream(ims, null);

                            //close stream
                            ims.close();

                        } catch (IOException e){
                            Log.e(TAG, e.getMessage());
                        }
                    }

                    //draw marker on map
                    MarkerUtil.addCustomMarker(map, newPos, "Waypoint " + nonVisitedWaypoints.get(0).getNumber(), addres, nonVisitedPointOfInterests.get(0), MarkerUtil.createCustomMarkerBitmap(MapsActivity.this, resource));

                    //if it hits the max possible requests OR max visible marker
                    if (locations.size() == 25 && nonVisitedWaypoints.size() >= 25) {
                        RouteUtil.routingWaypointsRequest(getApplicationContext(), locations, listener);
                        locations.clear();
                    } else if (locations.size() == MAX_MARKER_VISIBLE) {
                        RouteUtil.routingWaypointsRequest(getApplicationContext(), locations, listener);
                        locations.clear();
                    }

                    nonVisitedWaypoints.remove(nonVisitedWaypoints.get(0));

                    if (nonVisitedPointOfInterests.size() != 0){
                        nonVisitedPointOfInterests.remove(nonVisitedPointOfInterests.get(0));
                    }
                }

                //create current polyline
                if (userLocation != null && firstWaypoint != null && !firstWaypoint.isVisited())
                    RouteUtil.routingWaypointRequest(getApplicationContext(), MapUtil.getLatLngFromLocation(userLocation), new LatLng(firstWaypoint.getLat(), firstWaypoint.getLon()), listener);
            }
        });
    }

    private void updateGeofencing() {

        final Activity activity = this;
        final LifecycleOwner lifecycleOwner = this;

        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
            @Override
            public void onChanged(List<Waypoint> waypoints) {
                if (waypoints.isEmpty()) {
                    return;
                }
                final GeoFencing geoFencing = new GeoFencing(activity, lifecycleOwner);
                geoFencing.setGeofencingList(waypoints);
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = LocationUtil.getNewLocationRequest();

        boolean success = LocationUtil.checkLocationPermission(this);

        if (success) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        PopupUtil.showAlertDialog(this, "ERROR", "Your connection is suspended!", this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        PopupUtil.showAlertDialog(this, "ERROR " + connectionResult.getErrorCode(), connectionResult.getErrorMessage(), this);
    }

    @Override
    public void onMapLoaded() {
        drawRoute(this);
        updateGeofencing();
    }

    @Override
    public void onLocationChanged(Location location) {
        userLocation = location;

        if (loadingFirstTime) {
            MapUtil.moveCamera(map, MapUtil.getLatLngFromLocation(userLocation));
            loadingFirstTime = false;
        }
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> routeArrayList, int shortestRouteIndex, boolean isMultiple) {
        Log.d(TAG, "CustomRouting succes!");

        PolylineOptions polyOptions = new PolylineOptions();

        if (isMultiple) {
            polyOptions.color(ContextCompat.getColor(this, R.color.routeColor));
        } else {
            polyOptions.color(ContextCompat.getColor(this, R.color.routeCurrentColor));
        }

        polyOptions.width(15);
        polyOptions.addAll(routeArrayList.get(0).getPoints());
        map.addPolyline(polyOptions);
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.d(TAG, e.getMessage());
    }

    @Override
    public void onRoutingStart() {
        Log.d(TAG, "CustomRouting started!");
    }

    @Override
    public void onRoutingCancelled() {
        Log.d(TAG, "CustomRouting Cancelled!");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "Clicked on marker: " + marker.getTitle());

        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("selectedTitle", marker.getTitle());
        intent.putExtra("selectedPoI", (PointOfInterest) marker.getTag());
        this.startActivity(intent);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        //NOT IMPLEMENTED YET AND TESTING PURPOSES
    }

    @Override
    public void onChanged(List<PointOfInterest> pointOfInterests) {
        this.pointOfInterests = new ArrayList<>(pointOfInterests);
    }
}