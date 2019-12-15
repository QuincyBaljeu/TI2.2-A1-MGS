package com.example.ti22_a1_mgs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;

import com.example.ti22_a1_mgs.utils.LocationUtil;
import com.example.ti22_a1_mgs.utils.MapUtil;
import com.example.ti22_a1_mgs.utils.PopupUtil;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, DialogInterface.OnClickListener {

    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private LocationRequest locationRequest;

    //TODO migrate to FusedLocationProvider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            buildGoogleApiClient();
            MapUtil.setMapSettings(map);
            MapUtil.initializeMapCamera(map);
        } else {
            PopupUtil.showNotification(this, "ERROR", "Failed to load in tools for location listening.", this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
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
        //NOT IMPLEMENTED YET
    }
}