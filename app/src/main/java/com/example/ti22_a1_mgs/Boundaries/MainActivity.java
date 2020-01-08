package com.example.ti22_a1_mgs.Boundaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.example.ti22_a1_mgs.Database.RouteViewModel;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;
import com.example.ti22_a1_mgs.R;
import com.example.ti22_a1_mgs.utils.NetworkCheckerUtil;
import com.example.ti22_a1_mgs.utils.PopupUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RouteViewModel viewModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_id), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        if (!sharedPref.getBoolean(getString(R.string.preferences_database_loaded), false)) {
            viewModel.deleteAllDatabaseContents();
            final LifecycleOwner owner = this;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String json = JsonUtil.loadJSONFromAsset(context.getApplicationContext());
                    BlindWallsBreda blindWallsBreda = BlindWallsBreda.createFromJson(json);
                    viewModel.fillDatabaseFromData( blindWallsBreda.getAllWalls(), owner);
                }
            }, 3000);
            editor.putBoolean(getString(R.string.preferences_database_loaded), true);
        }

        Toolbar toolbar = findViewById(R.id.custom_action_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.name_main_activity);

        context = this;

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_map:

                        Intent intentMap = new Intent(
                                context,
                                MapsActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();

        //check network connection
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!NetworkCheckerUtil.isNetworkConnected(connectivityManager)){
             //false == no network found
            PopupUtil.showAlertDialog(this, "You are not connected to the internet!", "To access all features of this app you need to turn on you wifi or mobile data.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //nothing implemented
                }
            });
        }
    }
}
