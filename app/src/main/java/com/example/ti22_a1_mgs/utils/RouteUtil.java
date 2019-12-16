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

    public static synchronized void routingWaypointRequest(Context context, LatLng start, LatLng end, RoutingListener routingListener) {
        String key = context.getResources().getString(R.string.MGS_API_MAPS_DIRECTIONS_KEY);

        CustomRouting routing = new CustomRouting.Builder()
                .travelMode(CustomRouting.TravelMode.WALKING)
                .withListener(routingListener)
                .waypoints(start, end)
                .key(key)
                .build();
        routing.execute();
    }

    public static synchronized void routingWaypointsRequest(Context context, List<LatLng> waypointList, RoutingListener routingListener) {
        String key = context.getResources().getString(R.string.MGS_API_MAPS_DIRECTIONS_KEY);

        CustomRouting routing = new CustomRouting.Builder()
                .travelMode(CustomRouting.TravelMode.WALKING)
                .withListener(routingListener)
                .waypoints(waypointList)
                .key(key)
                .build();
        routing.execute();
    }

    public static void addToList(Polyline polyline){
        polylines.add(polyline);
    }

    public static List<Polyline> getList(){
        return polylines;
    }

    public static  void clearList(){
        for(Polyline line : polylines){
            line.remove();
        }
        polylines.clear();
    }
}
