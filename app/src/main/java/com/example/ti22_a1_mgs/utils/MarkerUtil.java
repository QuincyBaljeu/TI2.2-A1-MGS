package com.example.ti22_a1_mgs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.ti22_a1_mgs.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import de.hdodenhof.circleimageview.CircleImageView;

public class MarkerUtil {

    public static void addDefaultMarker(GoogleMap googleMap, LatLng latLng, String title, String subtitle) {
        MarkerOptions tempMarker = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(subtitle);

        googleMap.addMarker(tempMarker);
    }

    public static void addCustomMarker(GoogleMap googleMap, LatLng latLng, String title, String subtitle, Bitmap bitmap) {

        MarkerOptions tempMarker = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(subtitle)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap));

        googleMap.addMarker(tempMarker);
    }

    public static Bitmap createCustomMarkerBitmap(Context context, Drawable resource) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = marker.findViewById(R.id.circle_image_view);

        if(resource == null){
            markerImage.setImageResource(R.drawable.blindwalls_icon);
        } else {
            markerImage.setImageDrawable(resource);
        }


        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }
}
