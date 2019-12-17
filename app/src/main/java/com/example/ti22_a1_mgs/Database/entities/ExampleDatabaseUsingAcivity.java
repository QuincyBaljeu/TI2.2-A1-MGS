package com.example.ti22_a1_mgs.Database.entities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ti22_a1_mgs.Database.RouteViewModel;
import com.example.ti22_a1_mgs.R;

import java.util.List;

public abstract class ExampleDatabaseUsingAcivity extends AppCompatActivity {

    private static final String TAG = "ExampleDatabaseUsingAci";
    private RouteViewModel viewModelThing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.viewModelThing = ViewModelProviders.of(this).get(RouteViewModel.class);

        this.viewModelThing.getAllWayPoints().observe(this, new Observer<List<Waypoint>>() {
            @Override
            public void onChanged(List<Waypoint> waypoints) {
                //stuff that needs to happen when list is edited
                for (Waypoint waypoint : waypoints) {
//                    Log.wtf(TAG, viewModelThing.getPointOfInterestByLocationName(waypoint.getPointOfInterestId()).get(0).toString());
                }
            }
        });

        this.viewModelThing.getAllPointsOfInterest().observe(this, new Observer<List<PointOfInterest>>() {
            @Override
            public void onChanged(List<PointOfInterest> pointOfInterests) {
                //stuff that needs to happen when list is edited
            }
        });

        //
        this.viewModelThing.insert(new PointOfInterest(1, "het startpunt", "The start", null));
        this.viewModelThing.insert(new Waypoint(1, 3.24, 214.42, 1));

    }
}
