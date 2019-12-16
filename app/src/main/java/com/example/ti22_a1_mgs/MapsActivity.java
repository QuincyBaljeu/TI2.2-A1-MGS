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

public class MapsActivity extends AppCompatActivity, BroadcastReceiver
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
    private static final float GEOFENCE_RADIUS_IN_METERS = 25;
    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 1000*60*60;

    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList;
    private PendingIntent geofencePendingIntent;

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
        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceList = new ArrayList<>();
        addGeofence(3,3,"key");
        GeofencingRequest request = getGeofencingRequest();
        geofencingClient.addGeofences(request, getGeoFencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences added
                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add geofences
                        // ...
                    }
                });


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

    private PendingIntent getGeoFencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this,
                MapsActivity.class);
// We use FLAG_UPDATE_CURRENT
// so that we get the same pending intent back when calling
// addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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


    private void addGeoFence(double lat, double lon)
    {


    }

    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geofenceList);
        GeofencingRequest request = builder.build();
        return request;
    }

    private Geofence addGeofence(double lat, double lon, String key){
        geofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(key)

                .setCircularRegion(
                        lat,
                        lon,
                        GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
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
//            MapUtil.initializeMapCamera(map);

            map.setOnInfoWindowClickListener(this);
        } else {
            PopupUtil.showAlertDialog(this, "ERROR", "Failed to load in tools for location listening.", this);
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
            Polyline polyline = map.addPolyline(polyOptions);
            RouteUtil.addToList(polyline);
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

        final String imageUrl = "https://blindwalls.gallery/wp-content/uploads/2019/04/2015.09.23_GDFB_RUTGER_TERMOHLEN_Ralph_Roelse_Breda_NL_009-2000x1333.jpg";

        //later you can use marker object
        for (int i = 0; i < latLngList.size(); i++) {
            pos = i;
            MarkerUtil.getIconImage(imageUrl, MapsActivity.this);
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


    //For receiving Geofence Intents
    @Override
    public void onReceive(Context context, Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, geofencingEvent.toString());
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            List<String> keysToRemove = new ArrayList<String>();
            for (Geofence geofence : triggeringGeofences)
            {
                String key = geofence.getRequestId();
                keysToRemove.add(key);
                PopupUtil.showAlertDialog(this,  getString(R.string.waypoint_visited), "Duh", null);
            }
            geofencingClient.removeGeofences(keysToRemove);
            //Todo: Set waypoint to visited in database
            //Todo: update route

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );
            // Send notification and log the transition details.
            //sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, "BIG ERROR IN GEOFENCING");
        }
    }

    private String getGeofenceTransitionDetails(MapsActivity mapsActivity, int geofenceTransition, List<Geofence> triggeringGeofences) {
        return mapsActivity.toString() + " " + geofenceTransition + " " + triggeringGeofences.toString();
    }
}