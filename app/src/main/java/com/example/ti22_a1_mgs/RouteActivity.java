package com.example.ti22_a1_mgs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ti22_a1_mgs.Database.RouteViewModel;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity {

    private LiveData<List<PointOfInterest>> allPointsOfInterest;
    private RecyclerView recyclerView;
    private RouteViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

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

        model = ViewModelProviders.of(this).get(RouteViewModel.class);
        allPointsOfInterest = model.getAllPointsOfInterest();

        recyclerView = findViewById(R.id.location_recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(
                this, 1, GridLayoutManager.VERTICAL, false)
        );
        recyclerView.setAdapter(this.model.getPoiAdapter());

        this.model.getAllPointsOfInterest().observe(this, new androidx.lifecycle.Observer<List<PointOfInterest>>() {
            @Override
            public void onChanged(List<PointOfInterest> point) {
                model.getPoiAdapter().setPointOfInterests(point);
            }
        });


        this.model.getAllPointsOfInterest().observe(this, new Observer<List<PointOfInterest>>() {
            @Override
            public void onChanged(List<PointOfInterest> point) {
                model.reloadList(point);
            }
        });

    }
}
