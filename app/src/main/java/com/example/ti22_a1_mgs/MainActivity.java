package com.example.ti22_a1_mgs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private MainViewModel model;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        //model = ViewModelProviders.of(this).get(MainViewModel.class);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_map:

                        Intent intentMap = new Intent(
                                context,
                                MapActivity.class);
                        context.startActivity(intentMap);
                        break;

                    case R.id.navigation_locations:

                        Intent intentRoute = new Intent(
                                context,
                                RouteActivity.class);
                        context.startActivity(intentRoute);
                        break;

                    case R.id.navigation_help:

                        Intent intentHelp = new Intent(
                                context,
                                HelpActivity.class);
                        context.startActivity(intentHelp);
                        break;

                    case R.id.navigation_settings:

                        Intent intentSettings = new Intent(
                                context,
                                SettingsActivity.class);
                        context.startActivity(intentSettings);
                        break;
                }
                return true;
        }

    });
    }
}
