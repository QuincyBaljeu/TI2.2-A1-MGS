package com.example.ti22_a1_mgs.utils;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapUtil {

    private static final String TAG = MapUtil.class.getSimpleName();

    private static final float DEFAULT_CAMERA_ZOOM = 15.0f;

    public static void setMapStyling(Activity activity, GoogleMap googleMap) {
        //standard
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        //custom
//        try {
//            boolean success = googleMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            activity, R.raw.custom_map_style));
//
//            if (!success) {
//                Log.e(TAG, "Style parsing failed.");
//            }
//        } catch (Resources.NotFoundException e) {
//            Log.e(TAG, "Can't find style. Error: ", e);
//        }
    }

    public static void clearMap(GoogleMap googleMap) {
        googleMap.clear();
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

    public static void moveCamera(GoogleMap googleMap, LatLng latLng) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_CAMERA_ZOOM));
    }
}
