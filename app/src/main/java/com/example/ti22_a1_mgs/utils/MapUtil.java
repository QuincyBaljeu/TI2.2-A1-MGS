package com.example.ti22_a1_mgs.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ti22_a1_mgs.MapsActivity;
import com.example.ti22_a1_mgs.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapUtil {

    private static final String TAG = MapUtil.class.getSimpleName();

    private static final LatLngBounds LAT_LNG_BOUNDS =
            new LatLngBounds(
                    new LatLng(51.645891, 5.038042),
                    new LatLng(51.654991, 5.060769)
            );

    private static final float DEFAULT_CAMERA_ZOOM = 12.0f;


    public static void setMapStyling(Activity activity, GoogleMap googleMap) {
        //custom
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            activity, R.raw.custom_map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        //standard
//        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public static void initializeMapCamera(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(DEFAULT_CAMERA_ZOOM);
        googleMap.setLatLngBoundsForCameraTarget(LAT_LNG_BOUNDS);
    }

    public static void setMapSettings(GoogleMap googleMap) {
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setCompassEnabled(true);
        settings.setAllGesturesEnabled(true);

        googleMap.setMyLocationEnabled(true);
    }

    public static LatLng getLatLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static void moveCamera(GoogleMap googleMap, LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
}
