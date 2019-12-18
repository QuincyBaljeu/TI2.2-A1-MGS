package com.example.ti22_a1_mgs.Boundaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {

    private static final String TAG = DetailedActivity.class.getSimpleName();

    private TextView descriptionTextView, addressTextView;
    private List<SlideModel> images;
    private PointOfInterest pointOfInterestObject;
    private String pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Toolbar toolbar = findViewById(R.id.custom_action_bar);
        setSupportActionBar(toolbar);

        descriptionTextView = findViewById(R.id.descBw);
        addressTextView = findViewById(R.id.addressBw);

        pointOfInterestObject = (PointOfInterest) getIntent().getSerializableExtra("selectedPoI");
        pageTitle = getIntent().getStringExtra("selectedTitle");
        images = new ArrayList<>();

        initializeData();

        getSupportActionBar().setTitle(pageTitle);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        finish();
                        break;

                }
                return true;
            }

        });
    }

    private void initializeData() {
        addressTextView.setText(pointOfInterestObject.getAddres());

        //change description based on system language
        if (Locale.getDefault().getLanguage().equals("nl")) {
            descriptionTextView.setText(pointOfInterestObject.getNlDescription());
        } else {
            descriptionTextView.setText(pointOfInterestObject.getEnDescription());
        }

        for(String imageUrl : pointOfInterestObject.getImgUrls()){
            String refactoredImageUrl = imageUrl.replace("static/", "");
            images.add(new SlideModel("file:///android_asset/BWImages/" + refactoredImageUrl));
        }

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setImageList(images, true);
    }
}
