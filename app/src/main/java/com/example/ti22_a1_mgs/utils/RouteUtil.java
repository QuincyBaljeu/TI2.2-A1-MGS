package com.example.ti22_a1_mgs.utils;

import android.content.Context;
import android.util.Log;

import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.ti22_a1_mgs.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

public class RouteUtil {

    private static final String TAG = RouteUtil.class.getSimpleName();

    private static List<Polyline> polylines = new ArrayList<>();

    public static synchronized void routingWaypointRequest(Context context, LatLng start, LatLng end, CustomRoutingListener routingListener) {
        String key = context.getResources().getString(R.string.MGS_API_MAPS_DIRECTIONS_KEY);

        CustomRouting routing = new CustomRouting.Builder()
                .travelMode(CustomRouting.TravelMode.WALKING)
                .withListener(routingListener)
                .waypoints(start, end)
                .multiple(false)
                .key(key)
                .build();
        if(routing != null) routing.execute();
    }

    public static synchronized void routingWaypointsRequest(Context context, List<LatLng> waypointList, CustomRoutingListener routingListener) {
        String key = context.getResources().getString(R.string.MGS_API_MAPS_DIRECTIONS_KEY);

        CustomRouting routing = new CustomRouting.Builder()
                .travelMode(CustomRouting.TravelMode.WALKING)
                .withListener(routingListener)
                .waypoints(waypointList)
                .multiple(true)
                .key(key)
                .build();

        if(routing != null) routing.execute();
    }
}
