package com.example.ti22_a1_mgs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.example.ti22_a1_mgs.Database.RouteViewModel;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.utils.LocationUtil;
import com.example.ti22_a1_mgs.utils.MapUtil;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        DialogInterface.OnClickListener,
        RoutingListener,
        View.OnClickListener {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private LocationRequest locationRequest;

    //TODO migrate to FusedLocationProvider


    private RouteViewModel viewModelThing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        (findViewById(R.id.action_bar_button)).setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        this.viewModelThing = ViewModelProviders.of(this).get(RouteViewModel.class);

//        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
//            @Override
//            public void onChanged(List<Waypoint> waypoints) {
//                //stuff that needs to happen when list is edited
//                for (Waypoint waypoint : waypoints) {
//                    Log.wtf(TAG, waypoint.toString());
//                    printPointOfInterest(waypoint);
//                }
//            }
//        });
//
//        this.viewModelThing.getAllPointsOfInterest().observe(this, new Observer<List<PointOfInterest>>() {
//            @Override
//            public void onChanged(List<PointOfInterest> pointOfInterests) {
//                //stuff that needs to happen when list is edited
//                for (PointOfInterest pointOfInterest : pointOfInterests) {
//                    Log.wtf(TAG, pointOfInterest.toString());
//                }
//            }
//        });
    }

//    private void printPointOfInterest(Waypoint waypoint) {
//        this.viewModelThing.getPointOfInterestByLocationName(waypoint.getPointOfInterestId()).observe(this, new Observer<List<PointOfInterest>>() {
//            @Override
//            public void onChanged(List<PointOfInterest> pointOfInterests) {
//                for (PointOfInterest pointOfInterest : pointOfInterests) {
//                    Log.wtf("Je hebt Hem Kut Wohoo", pointOfInterest.toString());
//                }
//            }
//        });
//    }

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
            MapUtil.initializeMapCamera(map);
        } else {
            PopupUtil.showNotification(this, "ERROR", "Failed to load in tools for location listening.", this);
        }
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
        PopupUtil.showNotification(this, "ERROR", "Your connection is suspended!", this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        PopupUtil.showNotification(this, "ERROR " + connectionResult.getErrorCode(), connectionResult.getErrorMessage(), this);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        //NOT IMPLEMENTED YET AND TESTING PURPOSES
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.d(TAG, e.getMessage());
    }

    @Override
    public void onRoutingStart() {
        Log.d(TAG, "Routing started!");
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> routeArrayList, int shortestRouteIndex) {
        Log.d(TAG, "Routing succes!");

        //add route(s) to the map.
        for (int i = 0; i < routeArrayList.size(); i++) {

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(ContextCompat.getColor(this, R.color.colorAccent));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(routeArrayList.get(i).getPoints());
            Polyline polyline = map.addPolyline(polyOptions);
            RouteUtil.addToList(polyline);
        }
    }

    @Override
    public void onRoutingCancelled() {
        Log.d(TAG, "Routing Cancelled!");
    }

    @Override
    public void onClick(View view) {
        RouteUtil.routingWaypointRequest(this, MapUtil.getLatLngFromLocation(currentLocation), new LatLng(51.571915, 4.768323), this);
    }
}