package com.example.ti22_a1_mgs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.example.ti22_a1_mgs.utils.MapUtil;
import com.example.ti22_a1_mgs.utils.PopupUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        DialogInterface.OnClickListener
{

    private static final String TAG = MapsActivity.class.getSimpleName();

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 75;
    public static final LatLngBounds LAT_LNG_BOUNDS =
            new LatLngBounds(
                    new LatLng(51.645891, 5.038042),
                    new LatLng(51.654991, 5.060769)
            );

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        boolean success = MapUtil.checkLocationPermission(this);

        if (success) {
            if (fusedLocationClient == null) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            }
        } else {
            PopupUtil.showNotification(this, "ERROR", "Failed to load in tools for location listening.", this);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapUtil.setMapStyling(this, mMap);
        MapUtil.setMapSettings(mMap);
        MapUtil.initializeMapCamera(mMap);
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        //NOT IMPLEMENTED YET
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
