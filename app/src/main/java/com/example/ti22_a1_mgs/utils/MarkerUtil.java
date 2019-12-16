package com.example.ti22_a1_mgs.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.example.ti22_a1_mgs.R;
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

    private static Target target;

//    private static Target getTarget() {
//        if (target == null) {
//            synchronized (MarkerUtil.class) {
//                target = new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                }
//            }
//        }
//        return target;
//    }

    public static void getIconImage(String imageUrl, Target target) {
        if (imageUrl == null) {
            Picasso.get()
                    .load(R.drawable.blindwalls_icon)
                    .transform(getTransformationInstance())
                    .resize(DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT)
                    .into(target);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .transform(getTransformationInstance())
                    .resize(DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT)
                    .into(target);
        }

    }

    public static void addDefaultMarker(GoogleMap googleMap, LatLng latLng, String title, String subtitle) {
        MarkerOptions tempMarker = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(subtitle);

        googleMap.addMarker(tempMarker);
    }
}
