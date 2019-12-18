package com.example.ti22_a1_mgs.Boundaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.ti22_a1_mgs.Controllers.RouteAdapter;
import com.example.ti22_a1_mgs.Database.RouteViewModel;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity {

    private LiveData<List<PointOfInterest>> allPointsOfInterest;
    private RecyclerView recyclerView;
    private RouteViewModel model;
    private RouteAdapter routeAdapter;
    private List<PointOfInterest> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar = findViewById(R.id.custom_action_bar);
        setSupportActionBar(toolbar);
        dataSet = new ArrayList<>();
        recyclerView = findViewById(R.id.location_recyclerview);

       if (dataSet != null) {
           try {
               routeAdapter = new RouteAdapter(dataSet, this);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(routeAdapter);

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
        allPointsOfInterest.observe(this, new Observer<List<PointOfInterest>>() {
            @Override
            public void onChanged(List<PointOfInterest> pointOfInterests) {
                if (pointOfInterests.isEmpty()) return;
                routeAdapter.setDataSet(pointOfInterests);
                dataSet = pointOfInterests;
                routeAdapter.notifyDataSetChanged();
                Log.i("@d", "onChanged: "+ routeAdapter.getItemCount());
            }
        });


    }
}
