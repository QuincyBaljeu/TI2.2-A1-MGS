package com.example.ti22_a1_mgs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.example.ti22_a1_mgs.Database.RouteViewModel;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;
import com.example.ti22_a1_mgs.utils.LocationUtil;
import com.example.ti22_a1_mgs.utils.MapUtil;
import com.example.ti22_a1_mgs.utils.MarkerUtil;
import com.example.ti22_a1_mgs.utils.PopupUtil;

import com.example.ti22_a1_mgs.utils.RouteUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        DialogInterface.OnClickListener,
        RoutingListener,
        View.OnClickListener,
        Target,
        GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private LocationRequest locationRequest;

    private RouteViewModel viewModelThing;


    private RouteViewModel viewModelThing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        (findViewById(R.id.action_bar_button)).setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GeoFencing geoFencing = new GeoFencing(this);


        this.viewModelThing = ViewModelProviders.of(this).get(RouteViewModel.class);

//        this.viewModelThing.deleteAllDatabaseContents();


//        this.viewModelThing.fillDatabaseFromData(
//                this.viewModelThing.getBlindWallsBreda().getAllWalls(), this
//        );

//        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
//            @Override
//            public void onChanged(List<Waypoint> waypoints) {
//                //stuff that needs to happen when list is edited
//                for (Waypoint waypoint : waypoints) {
//                    Log.wtf(TAG, waypoint.toString());
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
//            MapUtil.initializeMapCamera(map);
            drawRoute(this);

            map.setOnInfoWindowClickListener(this);
        } else {
            PopupUtil.showAlertDialog(this, "ERROR", "Failed to load in tools for location listening.", this);
        }
    }

    private void drawRoute(final RoutingListener listener) {
        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
            @Override
            public void onChanged(List<Waypoint> waypoints) {
                ArrayList<LatLng> locations = new ArrayList<>();

                ArrayList<Waypoint> cloneWaypoint = new ArrayList<>(waypoints);
                while (cloneWaypoint.size() != 0) {
                    LatLng newPos = new LatLng(cloneWaypoint.get(0).getLat(), cloneWaypoint.get(0).getLon());
                    locations.add(newPos);
                    MarkerUtil.addDefaultMarker(map, newPos, "Waypoint " + cloneWaypoint.size(), UUID.randomUUID().toString().substring(0,10));
                    if (locations.size() == 25) {
                        RouteUtil.routingWaypointsRequest(getApplicationContext(), locations, listener);
                        locations.clear();
                    }
                    cloneWaypoint.remove(cloneWaypoint.get(0));
                }
                RouteUtil.routingWaypointsRequest(getApplicationContext(), locations, listener);
/*
                for (int i = 0; i < 25; i++) {
                    locations.add(new LatLng(waypoints.get(i).getLat(), waypoints.get(i).getLon()));
                    MarkerUtil.addDefaultMarker(map, locations.get(i), "Waypoint " + i, UUID.randomUUID().toString().substring(0,10));

                }
                Log.wtf(TAG, "Drawing the route");
                if (waypoints.isEmpty()) {
                    Log.wtf(TAG, locations.toString());
                    return;
                }
                Log.wtf(TAG, locations.toString());

                RouteUtil.routingWaypointsRequest(getApplicationContext(), locations, listener);
                RouteUtil.routingWaypointsRequest(getApplicationContext(), locations, listener);
                */

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
        Log.d(TAG, "CustomRouting started!");
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> routeArrayList, int shortestRouteIndex) {
        Log.d(TAG, "CustomRouting succes!");

        //add route(s) to the map.
        for (int i = 0; i < routeArrayList.size(); i++) {

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(ContextCompat.getColor(this, R.color.colorAccent));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(routeArrayList.get(i).getPoints());
            map.addPolyline(polyOptions);
        }
    }

    @Override
    public void onRoutingCancelled() {
        Log.d(TAG, "CustomRouting Cancelled!");
    }

    @Override
    public void onClick(View view) {
//        RouteUtil.routingWaypointRequest(this, MapUtil.getLatLngFromLocation(currentLocation), new LatLng(51.571915, 4.768323), this);

        latLngList.add(MapUtil.getLatLngFromLocation(currentLocation));
        latLngList.add(new LatLng(51.571915, 4.768323));
        latLngList.add(new LatLng(51.53083, 4.46528));

        RouteUtil.routingWaypointsRequest(this, latLngList, this);

        //later you can use marker object
        for (int i = 0; i < latLngList.size(); i++) {
            pos = i;
            MarkerUtil.addDefaultMarker(map, latLngList.get(i), "Waypoint " + pos, UUID.randomUUID().toString().substring(0,10));
        }
    }

    private List<LatLng> latLngList = new ArrayList<>();
    private int pos = 0;

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Log.d(TAG, "Bitmap loaded");
        MarkerOptions tempMarker = new MarkerOptions()
                .position(latLngList.get(pos))
                .title("Waypoint " + pos)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap));

        map.addMarker(tempMarker);
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        Log.e(TAG, e.getMessage());
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "Clicked on marker: " + marker.getTitle());
    }
}