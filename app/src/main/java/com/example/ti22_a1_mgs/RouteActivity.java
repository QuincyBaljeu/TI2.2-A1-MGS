package com.example.ti22_a1_mgs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {

    private ArrayList<PointOfInterestTestData> pointsOI;
    private RecyclerView recyclerView;
    private POIAdapter adapter;

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

        pointsOI = new ArrayList<>();
        createDataSet();

        recyclerView = findViewById(R.id.location_recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(
                this, 1, GridLayoutManager.VERTICAL, false)
        );
        adapter = new POIAdapter(pointsOI);
        recyclerView.setAdapter(adapter);



    }


    public void createDataSet(){
        int i;
        int random;

        int max = 1000;
        int min = 1;

        int range = max - min + 1;
        for (i = 0; i < 10; i++){
            random = (int)(Math.random() * range) + min;
            pointsOI.add(new PointOfInterestTestData("PointOfInterest:" + random, random));
        }
    }
}
