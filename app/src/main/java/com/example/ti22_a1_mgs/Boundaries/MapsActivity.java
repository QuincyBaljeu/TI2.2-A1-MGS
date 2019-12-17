package com.example.ti22_a1_mgs.Boundaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.example.ti22_a1_mgs.Database.RouteViewModel;
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

import java.util.ArrayList;
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
        GoogleMap.OnMapLoadedCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location userLocation;
    private LocationRequest locationRequest;

    private RouteViewModel viewModelThing;

    //todo remove polylines

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
            PopupUtil.showAlertDialog(this, "ERROR", "Failed to load in tools for location listening.", this);
        }
    }

    private void drawRoute(final CustomRoutingListener listener) {
        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
            @Override
            public void onChanged(List<Waypoint> waypoints) {
                //clear map
                MapUtil.clearMap(map);

                //create lists for data tracking
                ArrayList<LatLng> locations = new ArrayList<>();
                ArrayList<Waypoint> nonVistedClonedWaypoints = new ArrayList<>();

                //clone all non visited waypoints
                for (Waypoint waypoint : waypoints) {
                    if (!waypoint.isVisited()) {
                        nonVistedClonedWaypoints.add(waypoint);
                    }
                }

                //get first waypoint
                Waypoint firstWaypoint = nonVistedClonedWaypoints.get(0);

                //create the rest of the polylines
                while (nonVistedClonedWaypoints.size() != 0) {

                    LatLng newPos = new LatLng(nonVistedClonedWaypoints.get(0).getLat(), nonVistedClonedWaypoints.get(0).getLon());
                    //checks if waypoint has been visited if not it adds to the draw list
                    locations.add(newPos);

                    //draw marker on map
                    MarkerUtil.addCustomMarker(map, newPos, "Waypoint " + nonVistedClonedWaypoints.size(), UUID.randomUUID().toString().substring(0, 10), MarkerUtil.createCustomMarkerBitmap(MapsActivity.this, R.drawable.blindwalls_icon));

                    //if it hits the max possible requests
                    if (locations.size() == 25) {
                        RouteUtil.routingWaypointsRequest(getApplicationContext(), locations, listener);
                        locations.clear();
                    }

                    nonVistedClonedWaypoints.remove(nonVistedClonedWaypoints.get(0));
                }

                //create current polyline
                if (userLocation != null && firstWaypoint != null && !firstWaypoint.isVisited())
                    RouteUtil.routingWaypointRequest(getApplicationContext(), MapUtil.getLatLngFromLocation(userLocation), new LatLng(firstWaypoint.getLat(), firstWaypoint.getLon()), listener);

            }
        });
    }

    private void updateGeofencing(){

        final Activity activity = this;
        final LifecycleOwner lifecycleOwner = this;

        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
            @Override
            public void onChanged(List<Waypoint> waypoints) {
                if (waypoints.isEmpty()){
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
//        drawTestRoute(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        userLocation = location;
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
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        //NOT IMPLEMENTED YET AND TESTING PURPOSES
    }
}