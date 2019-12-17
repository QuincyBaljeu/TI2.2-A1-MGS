package com.example.ti22_a1_mgs;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ti22_a1_mgs.utils.PopupUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class GeoFencing extends BroadcastReceiver {

    private static final float GEOFENCE_RADIUS_IN_METERS = 25;
    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 1000 * 60 * 60;

    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList;
    private PendingIntent geofencePendingIntent;

    private Activity activity;

    public GeoFencing(Activity activity) {
        this.activity = activity;
        geofencingClient = LocationServices.getGeofencingClient(activity);
        geofenceList = new ArrayList<>();
        addGeofence(3, 3, "key");
        GeofencingRequest request = getGeofencingRequest();
        geofencingClient.addGeofences(request, getGeoFencePendingIntent())
                .addOnSuccessListener(activity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences added
                        // ...
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add geofences
                        // ...
                    }
                });
    }

    private static final String TAG = "GEOFENCING";

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
            for (Geofence geofence : triggeringGeofences) {
                String key = geofence.getRequestId();
                keysToRemove.add(key);

                PopupUtil.showAlertDialog(activity, activity.getResources().getString(R.string.waypoint_visited), "Duh", null);

            }
            geofencingClient.removeGeofences(keysToRemove);
            //Todo: Set waypoint to visited in database
            //Todo: update route

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    (MapsActivity) activity,
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

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geofenceList);
        GeofencingRequest request = builder.build();
        return request;
    }

    private void addGeofence(double lat, double lon, String key) {
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

    private PendingIntent getGeoFencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(activity,
                MapsActivity.class);
    // We use FLAG_UPDATE_CURRENT
    // so that we get the same pending intent back when calling
    // addGeofences() and removeGeofences().
        return PendingIntent.getService(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private String getGeofenceTransitionDetails(MapsActivity mapsActivity, int geofenceTransition, List<Geofence> triggeringGeofences) {
        return mapsActivity.toString() + " " + geofenceTransition + " " + triggeringGeofences.toString();
    }
}
