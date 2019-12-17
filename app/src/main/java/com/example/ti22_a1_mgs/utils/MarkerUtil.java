package com.example.ti22_a1_mgs.utils;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

public class MarkerUtil {

    private static final int DEFAULT_ICON_WIDTH = 200;
    private static final int DEFAULT_ICON_HEIGHT = 200;
    private static final int TRANSFORMATION_BORDER_WIDTH = 2;
    private static final int TRANSFORMATION_CORNER_RADIUS = 45;

    private static Transformation transformation;

    private static Transformation getTransformationInstance() {
        if (transformation == null) {
            synchronized (MarkerUtil.class) {
                transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.WHITE)
                        .borderWidthDp(TRANSFORMATION_BORDER_WIDTH)
                        .cornerRadiusDp(TRANSFORMATION_CORNER_RADIUS)
                        .oval(false)
                        .build();
            }
        }
        return transformation;
    }

    public static void getIconImage(String imageUrl, Target target) {
        Picasso.get()
                .load(imageUrl)
                .transform(getTransformationInstance())
                .resize(DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT)
                .into(target)
        ;
    }

    public static void addDefaultMarker(GoogleMap googleMap, LatLng latLng, String title, String subtitle) {
        MarkerOptions tempMarker = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(subtitle);

        googleMap.addMarker(tempMarker);
    }
}
